/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.jpa.entity.type.StandingOrderType;
import de.jreichl.jpa.repository.StandingOrderRepository;
import de.jreichl.service.exceptions.TransactionFailedException;
import java.sql.Timestamp;
import java.util.Calendar;
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
    @Schedule(second="*", minute="*/10",hour="*", persistent=false)
    public void handleStandingOrders(final Timer timer) {
        List<StandingOrder> orders = standingOrderRepo.findAll();
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format("Running handleStandingOrders with %d orders..",orders.size()));
        for(StandingOrder o : orders) {
            try {
                if(o.getLastTransaction()==null && o.getStartDate().getTime() < System.currentTimeMillis()) {
                    // first transaction!
                    transactionService.transfer(o, o.getStartDate());                           
                } else {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(o.getLastTransaction());                        
                    cal.add( o.getType().getCalendarType(), o.getType().equals(StandingOrderType.WEEKLY) ? 7 : 1 );
                    
                    if(cal.getTimeInMillis() < System.currentTimeMillis()) {
                        transactionService.transfer(o, new Timestamp(cal.getTimeInMillis()));
                    }                   
                }
            } catch (TransactionFailedException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to handle standing order with id=" + o.getId(), ex);
            }            
        }
    }
    
}
