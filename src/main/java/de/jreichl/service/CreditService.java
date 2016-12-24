/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.AccountTransaction;
import de.jreichl.jpa.entity.Bank;
import de.jreichl.jpa.entity.Credit;
import de.jreichl.jpa.entity.type.TransactionType;
import de.jreichl.jpa.repository.BankRepository;
import de.jreichl.jpa.repository.CreditRepository;
import de.jreichl.service.interfaces.ICreditService;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author Jürgen Reichl
 */
@RequestScoped
public class CreditService extends BaseService implements ICreditService {
    
    @Inject
    private BankRepository bankRepo;
    
    @Inject
    private CreditRepository creditRepo;
    
    @Transactional
    @Override
    public void updateInterestsToPay(Credit credit) {        
        Bank bank = bankRepo.getBank();
        List<AccountTransaction> transactions = credit.getTransactions();
        long remaining = credit.getCredit();
        for(AccountTransaction at : transactions) {            
            if(bank.getCreditAccount().equals(at.getAccount()) && at.getType().equals(TransactionType.CREDIT))
                remaining -= at.getAmount();
        }
        remaining += credit.getInterestToPay();
        if(remaining < 1) {
            credit.setPaybackComplete(true);            
        } else {
            long interestToPay = credit.getInterestToPay();
            interestToPay += remaining * (credit.getInterestRate() * 0.0001);
            credit.setInterestToPay(interestToPay);            
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(credit.getInterestAddedDate());  
            cal.add(Calendar.MONTH, 1 );
            credit.setInterestAddedDate(new Date(cal.getTimeInMillis()));
        }
        
        creditRepo.persist(credit);
    }

    @Override
    public Credit takeCredit(Account account, long amountInCent, java.util.Date creationDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Credit payback(Account fromAccount, Credit credit, long amountInCent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
