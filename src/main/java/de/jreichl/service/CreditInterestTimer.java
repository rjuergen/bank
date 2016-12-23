/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Credit;
import de.jreichl.jpa.repository.CreditRepository;
import de.jreichl.service.interfaces.ICreditService;
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
 * Timer to add interests (Zinsen) to the given credit
 * @author Jürgen Reichl
 */
@Stateless
public class CreditInterestTimer {
    
    @Inject
    private CreditRepository creditRepo;
    
    @Inject
    private ICreditService creditService;
    
    /**
     * runs every day at 1:00 o'clock
     * @param timer 
     */ 
    @Schedule(hour="1", persistent=false)
    public void handleCreditInterests(final Timer timer) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format("%s - Running handleCreditInterests..", new Date().toString()));
        List<Credit> credits = creditRepo.findAllUnpaid();
        for(Credit c : credits) {               
            Calendar cal = Calendar.getInstance();
            cal.setTime(c.getInterestAddedDate());  
            cal.add(Calendar.MONTH, 1 );
            while(cal.getTimeInMillis() < System.currentTimeMillis()) {
                // update interests
                creditService.updateInterestsToPay(c);
                
                if(c.isPaybackComplete())
                    break;
                
                cal.setTime(c.getInterestAddedDate());  
                cal.add(Calendar.MONTH, 1 );
            }    
        }
    }
    
}
