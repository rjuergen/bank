/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.PrivateCustomer;

/**
 *
 * @author Jürgen Reichl
 */
public class PrivateCustomerRepository extends CustomerRepository<PrivateCustomer> {
    
    public PrivateCustomerRepository() {
        super(PrivateCustomer.class);
    }
    
}
