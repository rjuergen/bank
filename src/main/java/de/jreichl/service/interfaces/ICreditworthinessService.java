/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.interfaces;

import de.jreichl.jpa.entity.Creditworthiness;
import de.jreichl.jpa.entity.Customer;

/**
 *
 * @author Jürgen Reichl
 */
public interface ICreditworthinessService {
    
    public Creditworthiness requestCreditworthiness(Customer customer);
    
    public Creditworthiness requestCreditworthinessFromRetailerPoschinger(Customer customer);
    
}
