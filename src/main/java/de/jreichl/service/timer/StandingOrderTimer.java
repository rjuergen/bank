/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.timer;

import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.jpa.repository.StandingOrderRepository;
import de.jreichl.service.interfaces.IStandingOrderService;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.inject.Inject;

/**
 * Timer which runs on the server in defined intervals. Handles the standing orders.
 * @author Jürgen Reichl
 */
@Stateless
public class StandingOrderTimer extends BaseTimer {
    
    @Inject
    private StandingOrderRepository standingOrderRepo;    
    
    @Inject
    private IStandingOrderService standingOrderService;
    
    
    /**
     * runs every 10 minutes
     * @param timer 
     */ 
    @Schedule(minute="*/10",hour="*", persistent=false)
    public void handleStandingOrders(final Timer timer) {
        logger.log(Level.INFO, String.format("%s - Running handleStandingOrders..", new Date().toString()));
        List<StandingOrder> orders = standingOrderRepo.findAll();        
        for(StandingOrder o : orders) {
            try {
                standingOrderService.handleStandingOrder(o);                
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Failed to handle standing order with id=" + o.getId(), ex);
            }            
        }
    }
    
}
