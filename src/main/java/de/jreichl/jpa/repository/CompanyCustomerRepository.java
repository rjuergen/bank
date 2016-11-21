/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.CompanyCustomer;

/**
 *
 * @author Jürgen Reichl
 */
public class CompanyCustomerRepository extends CustomerRepository<CompanyCustomer> {
    
    public CompanyCustomerRepository() {
        super(CompanyCustomer.class);
    }
    
}
