/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.StandingOrder;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Jürgen Reichl
 */
@RequestScoped
public class StandingOrderRepository extends SingleEntityRepository<StandingOrder> {
  
}
