/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.jpa.entity.type.IntervalUnit;
import de.jreichl.jpa.repository.AccountRepository;
import de.jreichl.jpa.repository.StandingOrderRepository;
import java.sql.Timestamp;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.transaction.Transactional;

/**
 * Service to create standing orders (Dauerauftraege)
 * @author Jürgen Reichl
 */
@RequestScoped
@WebService
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
     * @param interval the interval (in interval unit) for e.g. if you choose IntervalUnit.Hourly and have an interval=3 the standing order will run every 3 hours.
     * @param unit interval unit (hourly, daily, weekly, monthly or yearly)
     * @param description short description of the standing order
     * @return the created standing order
     */
    @Transactional
    @WebMethod
    public StandingOrder createStandingOrder(String fromIBAN, String toIBAN, long amountInCent, Date startDate, int interval, IntervalUnit unit, String description) {
        StandingOrder o = new StandingOrder();
        
        Account fromAccount = accountRepo.findByIBAN(fromIBAN);
        Account toAccount = accountRepo.findByIBAN(toIBAN);
        
        o.setFromAccount(fromAccount);
        o.setToAccount(toAccount);
        o.setInterval(interval);
        o.setIntervalUnit(unit);
        o.setAmount(amountInCent);
        o.setStartDate(new Timestamp(startDate.getTime()));
        o.setDescription(description);
        
        standingOrderRepo.persist(o);
        
        return o;
    }
    
}
