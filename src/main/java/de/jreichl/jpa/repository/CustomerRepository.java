/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.Customer;

/**
 *
 * @author Jürgen Reichl
 */
public class CustomerRepository extends SingleEntityRepository<Customer> {
    
    public CustomerRepository() {
        super(Customer.class);
    }
    
}
