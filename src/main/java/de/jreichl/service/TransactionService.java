/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.AccountTransaction;
import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.jpa.entity.type.TransactionType;
import de.jreichl.jpa.repository.AccountRepository;
import de.jreichl.jpa.repository.AccountTransactionRepository;
import de.jreichl.jpa.repository.StandingOrderRepository;
import de.jreichl.service.exceptions.TransactionFailedException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

/**
 * Service to transfer money to different accounts
 * @author Jürgen Reichl
 */
@RequestScoped
@WebService
public class TransactionService {   
    
    
    @Inject
    private AccountTransactionRepository accountTransactionRepo;
    
    @Inject 
    private AccountRepository accountRepo;
    
    @Inject
    private StandingOrderRepository standingOrderRepo;
    
    /**
     * method to transfer amount in cent from one account(fromIBAN) to another(toIBAN)
     * @param amountInCent amount in cent
     * @param fromIBAN the account to debit(-)
     * @param toIBAN the account to credit(+)
     * @return true if account transactions were successfully
     * @throws TransactionFailedException 
     */
    @Transactional
    @WebMethod
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

            return transfer(amountInCent, fromAccount, toAccount, description);
            
        } catch(TransactionFailedException ex) {
            throw ex;
        } catch(Exception ex) {
            throw new TransactionFailedException(ex, "Transaction failed!", fromIBAN, toIBAN, currentDate, amountInCent);
        }
    }
    
    @Transactional
    boolean transfer(StandingOrder order, Timestamp newLastTransactionDate) throws TransactionFailedException {
        transfer(order.getAmount(), order.getFromAccount(), order.getToAccount(), order.getDescription());                        
        order.setLastTransaction(newLastTransactionDate);
        standingOrderRepo.persist(order);
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format(" # %s standing order(id=%d) successfull handled!", order.getIntervalUnit().name() ,order.getId()) );
        return true;
    }
    
    @Transactional
    boolean transfer(long amountInCent, Account fromAccount, Account toAccount, String description) throws TransactionFailedException {
        Date currentDate = new Date();
        
        try {
        
            // create Transaction
            AccountTransaction t1 = new AccountTransaction(fromAccount, TransactionType.DEBIT, amountInCent, new java.sql.Timestamp(currentDate.getTime()));        
            t1.setDescription(description);
            
            AccountTransaction t2 = new AccountTransaction(toAccount, TransactionType.CREDIT, amountInCent, new java.sql.Timestamp(currentDate.getTime()));
            t2.setDescription(description);
            
            // persist
            accountTransactionRepo.persist(t1);
            accountTransactionRepo.persist(t2);

        } catch(Exception ex) {
            throw new TransactionFailedException(ex, "Transaction failed!", fromAccount.getIban(), toAccount.getIban(), currentDate, amountInCent);
        }
        
        return true;
    }
        
}
