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
    
    @Inject AccountRepository accountRepo;
    
    @Transactional
    public AccountTransaction createTransaction(long amountInCent, TransactionType type, String IBAN) throws TransactionFailedException {        
         // get current/transaction date
        Date currentDate = new Date();
        
        try {     
            Account account = null;
            try{
                // get account with given IBAN
                account = accountRepo.findByIBAN(IBAN);     
            } catch (NoResultException nrex) {
                throw new TransactionFailedException(nrex, String.format("Transaction failed! %s is not a valid IBAN", IBAN), IBAN, currentDate, type, amountInCent);
            }

            // create Transaction
            AccountTransaction t = new AccountTransaction(account, type, amountInCent, new java.sql.Date(currentDate.getTime()));        

            // persist
            accountTransactionRepo.persist(t);
            
            return t;
        } catch(TransactionFailedException ex) {
            throw ex;
        } catch(Exception ex) {
            throw new TransactionFailedException(ex, "Transaction failed!", IBAN, currentDate, type, amountInCent);
        }
    }
}
