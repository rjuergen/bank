/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.common.AmountUtil;
import de.jreichl.common.Pair;
import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.AccountTransaction;
import de.jreichl.jpa.entity.Bank;
import de.jreichl.jpa.entity.Credit;
import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.jpa.entity.type.TransactionType;
import de.jreichl.jpa.repository.AccountRepository;
import de.jreichl.jpa.repository.AccountTransactionRepository;
import de.jreichl.jpa.repository.BankRepository;
import de.jreichl.jpa.repository.CreditRepository;
import de.jreichl.jpa.repository.StandingOrderRepository;
import de.jreichl.service.exception.TransactionFailedException;
import de.jreichl.service.interfaces.ITransactionService;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

/**
 * Service to transfer money to different accounts
 * @author Jürgen Reichl
 */
@RequestScoped
public class TransactionService extends BaseService implements ITransactionService {   
       
    
    @Inject
    private AccountTransactionRepository accountTransactionRepo;
    
    @Inject 
    private AccountRepository accountRepo;
    
    @Inject
    private StandingOrderRepository standingOrderRepo;
    
    @Inject
    private CreditRepository creditRepo;
    
    @Inject
    private BankRepository bankRepo;
    
    /**
     * method to transfer amount in cent from one account(fromIBAN) to another(toIBAN)
     * @param amountInCent amount in cent
     * @param fromIBAN the account to debit(-)
     * @param toIBAN the account to credit(+)
     * @param description description of the transaction
     * @return true if account transactions were successfully
     * @throws TransactionFailedException 
     */
    @Transactional(value = TxType.REQUIRED, rollbackOn = { TransactionFailedException.class })   
    @Override
    public boolean transfer(long amountInCent, String fromIBAN, String toIBAN, String description) throws TransactionFailedException {        
        // get current/transaction date
        Date currentDate = new Date();
        
        try {
            Account fromAccount = null;
            Account toAccount = null;
            try{
                // get account with given IBAN
                fromAccount = accountRepo.findByIBAN(fromIBAN);     
            } catch (NoResultException nrex) {
                throw new TransactionFailedException(nrex, String.format("Transaction failed! %s is not a valid IBAN", fromIBAN), fromIBAN, toIBAN, currentDate, amountInCent);
            }
            
            try{
                // get account with given IBAN
                toAccount = accountRepo.findByIBAN(toIBAN);     
            } catch (NoResultException nrex) {
                throw new TransactionFailedException(nrex, String.format("Transaction failed! %s is not a valid IBAN", toIBAN), fromIBAN, toIBAN, currentDate, amountInCent);
            }

            Pair<AccountTransaction, AccountTransaction> result = transfer(amountInCent, fromAccount, toAccount, description);
            return result.getA() != null && result.getB() != null;
                    
        } catch(TransactionFailedException ex) {
            throw ex;
        } catch(Exception ex) {
            throw new TransactionFailedException(ex, "Transaction failed!", fromIBAN, toIBAN, currentDate, amountInCent);
        }
    }
    
    @Transactional(value = TxType.REQUIRED, rollbackOn = { TransactionFailedException.class })
    @Override
    public boolean transferStandingOrder(StandingOrder order, Timestamp newLastTransactionDate) throws TransactionFailedException {        
        order = standingOrderRepo.merge(order);
        Pair<AccountTransaction, AccountTransaction> result = transfer(order.getAmount(), order.getFromAccount(), order.getToAccount(), order.getDescription());  
        if(order.getForCredit()!=null) {
            Credit credit = creditRepo.merge(order.getForCredit());
            credit.addTransaction(result.getA());
            credit.addTransaction(result.getB());            
        }
        order.setLastTransaction(newLastTransactionDate);        
        logger.log(Level.INFO, String.format(" # %s standing order(id=%d) successfull handled!", order.getIntervalUnit().name() ,order.getId()) );
        return true;
    }    

    @Transactional(value = TxType.REQUIRED, rollbackOn = { TransactionFailedException.class })
    @Override
    public boolean transferCashCredit(long amountInCent, String toIBAN, String description) throws TransactionFailedException {
        // get current/transaction date
        Date currentDate = new Date();
        
        try {
            Account toAccount = null;     
            
            try{
                // get account with given IBAN
                toAccount = accountRepo.findByIBAN(toIBAN);     
            } catch (NoResultException nrex) {
                throw new TransactionFailedException(nrex, String.format("Transaction failed! %s is not a valid IBAN", toIBAN), null, toIBAN, currentDate, amountInCent);
            }

            Pair<AccountTransaction, AccountTransaction> result = transfer(amountInCent, null, toAccount, description);
            return result.getB() != null;
            
        } catch(TransactionFailedException ex) {
            throw ex;
        } catch(Exception ex) {
            throw new TransactionFailedException(ex, "Transaction failed!", null, toIBAN, currentDate, amountInCent);
        }
    }

    @Transactional(value = TxType.REQUIRED, rollbackOn = { TransactionFailedException.class })
    @Override
    public boolean transferCashDebit(long amountInCent, String fromIBAN, String description) throws TransactionFailedException {
        // get current/transaction date
        Date currentDate = new Date();
        
        try {
            Account fromAccount = null;     
            
            try{
                // get account with given IBAN
                fromAccount = accountRepo.findByIBAN(fromIBAN);     
            } catch (NoResultException nrex) {
                throw new TransactionFailedException(nrex, String.format("Transaction failed! %s is not a valid IBAN", fromIBAN), fromIBAN, null, currentDate, amountInCent);
            }

            Pair<AccountTransaction, AccountTransaction> result = transfer(amountInCent, fromAccount, null, description);
            return result.getA() != null;
            
        } catch(TransactionFailedException ex) {
            throw ex;
        } catch(Exception ex) {
            throw new TransactionFailedException(ex, "Transaction failed!", fromIBAN, null, currentDate, amountInCent);
        }
    }
    
    @Transactional(value = TxType.REQUIRED, rollbackOn = { TransactionFailedException.class })
    private Pair<AccountTransaction, AccountTransaction> transfer(long amountInCent, Account fromAccount, Account toAccount, String description) throws TransactionFailedException {
        Date currentDate = new Date();
        AccountTransaction t1 = null;
        AccountTransaction t2 = null;
        try {
        
            if(fromAccount != null) {
                fromAccount = accountRepo.merge(fromAccount);
                long balance = fromAccount.getBalance();
                if(balance - amountInCent < 0) {
                    String msg = String.format("Transaktion abgebrochen! Nicht genügend Geld auf dem Konto mit der IBAN %s vorhanden.", fromAccount.getIban());                    
                    throw new TransactionFailedException(null, msg,  fromAccount.getIban(), toAccount.getIban(), currentDate, amountInCent);
                }
                t1 = new AccountTransaction(fromAccount, TransactionType.DEBIT, amountInCent, new java.sql.Timestamp(currentDate.getTime()));        
                t1.setDescription(description);    
                fromAccount.addTransaction(t1);              
            }
            
            if(toAccount != null) {
                toAccount = accountRepo.merge(toAccount);
                t2 = new AccountTransaction(toAccount, TransactionType.CREDIT, amountInCent, new java.sql.Timestamp(currentDate.getTime()));
                t2.setDescription(description);
                toAccount.addTransaction(t2);              
            }
            
            if(t1 != null && t2 != null) {
                t1.setAssociatedTransaction(t2);
                t2.setAssociatedTransaction(t1);
            }
            
            if(t1 !=null)
                accountTransactionRepo.persist(t1);
            
            if(t2 !=null)
                accountTransactionRepo.persist(t2);
            
        } catch(TransactionFailedException ex) {
            throw ex;
        } catch(Exception ex) {
            throw new TransactionFailedException(ex, "Transaction failed!", fromAccount!=null ? fromAccount.getIban() : null,
                    toAccount!=null ? toAccount.getIban() : null, currentDate, amountInCent);
        }
        return new Pair<>(t1, t2);
    }

    @Transactional(value = TxType.REQUIRED, rollbackOn = { TransactionFailedException.class })
    @Override
    public boolean transferCredit(Credit credit) throws TransactionFailedException {
        credit = creditRepo.merge(credit);
        Bank bank = bankRepo.getBank();
        String description = String.format("New credit about %s with ID=%s (date of creation: %s)",
                credit.getCreditFormatted(), credit.getId().toString(), credit.getCreationDate().toString());
        Pair<AccountTransaction, AccountTransaction> result = transfer(credit.getCredit(), bank.getCreditAccount(), credit.getAccount(), description);         
        credit.addTransaction(result.getB());        
        return result.getA() != null && result.getB() != null;
    }
    
    @Transactional(value = TxType.REQUIRED, rollbackOn = { TransactionFailedException.class })
    @Override
    public boolean transferPayback(Credit credit, long amountInCent) throws TransactionFailedException {
        credit = creditRepo.merge(credit);
        Bank bank = bankRepo.getBank();        
        String amountFormatted = AmountUtil.getFormattedAmount(amountInCent);        
        String description = String.format("Credit payback about %s. Credit-ID=%s (date of creation: %s)",
                amountFormatted, credit.getId().toString(), credit.getCreationDate().toString());
        Pair<AccountTransaction, AccountTransaction> result = transfer(amountInCent, credit.getAccount(), bank.getCreditAccount(), description);         
        credit.addTransaction(result.getA());
        return result.getA() != null && result.getB() != null;
    }
    
    @Override
    public void checkInput(long amountInCent, String fromIBAN, String toIBAN) throws TransactionFailedException {
        try {
            // check IBAN 
            Account fromAccount = accountRepo.findByIBAN(fromIBAN);
            
            // check balance 
            long balance = fromAccount.getBalance();
            if(balance - amountInCent < 0) {
                String msg = String.format("Transaction failed! Not enough money on account with IBAN %s.", fromAccount.getIban());                    
                throw new TransactionFailedException(null, msg, fromIBAN, toIBAN, new Date(), amountInCent);
            }
                        
            try {
                // check IBAN 
                accountRepo.findByIBAN(toIBAN);                
            } catch (NoResultException ex) {
                throw new TransactionFailedException(ex, String.format("Transaction failed! %s is not a valid IBAN", toIBAN), fromIBAN, toIBAN, new Date(), amountInCent);
            }
            
        } catch (NoResultException ex) {
            throw new TransactionFailedException(ex, String.format("Transaction failed! %s is not a valid IBAN", fromIBAN), fromIBAN, toIBAN, new Date(), amountInCent);
        } catch (TransactionFailedException ex) { 
            throw ex;
        } catch (Exception ex) {
            throw new TransactionFailedException(ex, "Unexpected Error! Transaction failed. Check if it's the right IBAN and there is enough money on your account.", fromIBAN, toIBAN, new Date(), amountInCent);
        }
    }
        
}
