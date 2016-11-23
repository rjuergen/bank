/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.AccountTransaction;
import de.jreichl.jpa.entity.type.TransactionType;
import de.jreichl.jpa.repository.AccountRepository;
import de.jreichl.jpa.repository.AccountTransactionRepository;
import de.jreichl.service.exceptions.TransactionFailedException;
import java.util.Date;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

/**
 *
 * @author Jürgen Reichl
 */
public class TransactionService {   
    
    
    @Inject
    private AccountTransactionRepository accountTransactionRepo;
    
    @Inject 
    private AccountRepository accountRepo;
    
    /**
     * method to transfer amount in cent from one account(fromIBAN) to another(toIBAN)
     * @param amountInCent amount in cent
     * @param fromIBAN the account to debit(-)
     * @param toIBAN the account to credit(+)
     * @return true if account transactions were successfully
     * @throws TransactionFailedException 
     */
    @Transactional
    public boolean transfer(long amountInCent, String fromIBAN, String toIBAN) throws TransactionFailedException {        
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

            // create Transaction
            AccountTransaction t1 = new AccountTransaction(fromAccount, TransactionType.DEBIT, amountInCent, new java.sql.Date(currentDate.getTime()));        

            AccountTransaction t2 = new AccountTransaction(toAccount, TransactionType.CREDIT, amountInCent, new java.sql.Date(currentDate.getTime()));
            
            // persist
            accountTransactionRepo.persist(t1);
            accountTransactionRepo.persist(t2);
            
            return true;
        } catch(TransactionFailedException ex) {
            throw ex;
        } catch(Exception ex) {
            throw new TransactionFailedException(ex, "Transaction failed!", fromIBAN, toIBAN, currentDate, amountInCent);
        }
    }
        
}
