/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.interfaces;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.jpa.entity.type.IntervalUnit;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jürgen Reichl
 */
public interface IStandingOrderService {
 
    public StandingOrder createStandingOrder(String fromIBAN, String toIBAN, long amountInCent, Date startDate, int interval, IntervalUnit unit, String description);
    
    public boolean deleteStandingOrder(StandingOrder toDelete);
    
    public List<StandingOrder> getStandingOrders(Account fromAccount);
    
}
