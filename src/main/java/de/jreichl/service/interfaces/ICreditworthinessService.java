/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.interfaces;

import de.jreichl.jpa.entity.Customer;
import net.poschinger.retailerposchinger.service.contractor.CreditWorthiness;

/**
 *
 * @author Jürgen Reichl
 */
public interface ICreditworthinessService {
    
    public CreditWorthiness requestCreditworthiness(Customer customer);
    
    public CreditWorthiness requestCreditworthinessFromRetailerPoschinger(Customer customer);
    
}
