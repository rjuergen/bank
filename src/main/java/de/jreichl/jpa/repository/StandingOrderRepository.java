/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.StandingOrder;

/**
 *
 * @author Jürgen Reichl
 */
public class StandingOrderRepository extends SingleEntityRepository<StandingOrder> {
    
    public StandingOrderRepository() {
        super(StandingOrder.class);
    }
    
}
