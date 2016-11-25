/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.jpa.entity.type.StandingOrderType;
import de.jreichl.jpa.repository.AccountRepository;
import de.jreichl.jpa.repository.StandingOrderRepository;
import java.sql.Timestamp;
import java.util.Date;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author Jürgen Reichl
 */
public class StandingOrderService {
    
    @Inject
    private StandingOrderRepository standingOrderRepo;
    
    @Inject
    private AccountRepository accountRepo;
    
    /**
     * create a standing order.
     * a standing order transfers amount in cent from one account(fromIBAN) to another(toIBAN) in regular intervals     
     * @param fromIBAN the account to debit(-)
     * @param toIBAN the account to credit(+)
     * @param amountInCent amount in cent
     * @param startDate date to start with the transfers
     * @param type interval type (hourly, daily, weekly, monthly or yearly)
     * @param description short description of the standing order
     * @return the created standing order
     */
    @Transactional
    public StandingOrder createStandingOrder(String fromIBAN, String toIBAN, long amountInCent, Date startDate, StandingOrderType type, String description) {
        StandingOrder o = new StandingOrder();
        
        Account fromAccount = accountRepo.findByIBAN(fromIBAN);
        Account toAccount = accountRepo.findByIBAN(toIBAN);
        
        o.setFromAccount(fromAccount);
        o.setToAccount(toAccount);
        o.setType(type);
        o.setAmount(amountInCent);
        o.setStartDate(new Timestamp(startDate.getTime()));
        o.setDescription(description);
        
        standingOrderRepo.persist(o);
        
        return o;
    }
    
}
