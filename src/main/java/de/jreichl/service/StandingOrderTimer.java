/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.jpa.repository.StandingOrderRepository;
import de.jreichl.service.exceptions.TransactionFailedException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.inject.Inject;

/**
 * Timer which runs on the server in defined intervals. Handles the standing orders.
 * @author Jürgen Reichl
 */
@Stateless
public class StandingOrderTimer {
    
    @Inject
    private StandingOrderRepository standingOrderRepo;
    
    @Inject
    private TransactionService transactionService;
    
    
    /**
     * runs every 10 minutes
     * @param timer 
     */ 
    @Schedule(minute="*/10",hour="*", persistent=false)
    public void handleStandingOrders(final Timer timer) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format("%s - Running handleStandingOrders..", new Date().toString()));
        List<StandingOrder> orders = standingOrderRepo.findAll();        
        for(StandingOrder o : orders) {
            try {
                if(o.getLastTransaction()==null && o.getStartDate().getTime() < System.currentTimeMillis()) {                    
                    // first transaction!
                    transactionService.transfer(o, o.getStartDate());                    
                }
                if(o.getLastTransaction()!=null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(o.getLastTransaction());  
                    cal.add(o.getIntervalUnit().getCalendarType(), o.getIntervalUnit().getCalendarAmount() * o.getInterval() );
                    while(cal.getTimeInMillis() < System.currentTimeMillis()) {
                        transactionService.transfer(o, new Timestamp(cal.getTimeInMillis()));
                        
                        cal.setTime(o.getLastTransaction());  
                        cal.add(o.getIntervalUnit().getCalendarType(), o.getIntervalUnit().getCalendarAmount() * o.getInterval() );
                    }                    
                }
            } catch (TransactionFailedException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to handle standing order with id=" + o.getId(), ex);
            }            
        }
    }
    
}
