/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.Bank;
import de.jreichl.jpa.entity.Credit;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.jpa.entity.type.IntervalUnit;
import de.jreichl.jpa.repository.BankRepository;
import de.jreichl.jpa.repository.CreditRepository;
import de.jreichl.service.exception.TransactionFailedException;
import de.jreichl.service.interfaces.ICreditService;
import de.jreichl.service.interfaces.ICustomerService;
import de.jreichl.service.interfaces.IStandingOrderService;
import de.jreichl.service.interfaces.ITransactionService;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
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
    
    @Inject
    private IStandingOrderService standingOrderService;
    
    @Inject
    private ICustomerService customerService;
    
    @Inject
    private ITransactionService transactionService;
    
    @Transactional
    @Override
    public void updateInterestsToPay(Credit credit) {
        long remaining = credit.getRemainingPayback();
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
    

    @Transactional
    @Override
    public Credit takeCredit(Account account, long amountInCent, int interestRateInPerTenThousand, java.util.Date creationDate) throws TransactionFailedException {        
        Credit c = new Credit();
        c.setAccount(account);
        c.setCreationDate(new Timestamp(creationDate.getTime()));
        c.setCredit(amountInCent);
        c.setInterestRate(interestRateInPerTenThousand);
                
        Customer customer = customerService.findCustomer(account.getOwner());
        customer.addCredit(c);                              
        
        creditRepo.persist(c);
        customerService.persistCustomer(customer);
        
        // transfer credit to account
        transactionService.transferCredit(c);
        
        return c;
    }

    @Transactional
    @Override
    public Credit payback(Credit credit, long amountInCent) throws TransactionFailedException {        
        transactionService.transferPayback(credit, amountInCent);
        return credit;
    }

    @Transactional
    @Override
    public StandingOrder updatePaybackStandingOrder(Account fromAccount, Credit credit, long monthlyAmountInCent) {
        Bank bank = bankRepo.getBank();
        credit = creditRepo.merge(credit);
        if(credit.getStandingOrder() != null) {
            credit.setStandingOrder(null);
            standingOrderService.deleteStandingOrder(credit.getStandingOrder());
        }
        String description = String.format("Monthly standing order for credit with ID=%s (date of creation: %s)",
                credit.getId().toString(), credit.getCreationDate().toString());
        StandingOrder order = standingOrderService.createStandingOrder(credit.getAccount().getIban(),
                bank.getCreditAccount().getIban(), monthlyAmountInCent, new java.util.Date(), 1, IntervalUnit.MONTHLY, description);
        credit.setStandingOrder(order);
        creditRepo.persist(credit);
        return order;
    }
    
}
