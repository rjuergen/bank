/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.PrivateCustomer;
import de.jreichl.jpa.entity.type.Gender;
import de.jreichl.jpa.repository.CustomerRepository;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author Jürgen Reichl
 */
public class CustomerService {
    
    @Inject
    private CustomerRepository repo;
 
    @Transactional
    public PrivateCustomer createDummyPrivateCustomer() {
        // PrivateCustomer (entity) anlegen (im Heap)
        PrivateCustomer c = new PrivateCustomer();
        c.setFirstName("Hans");
        c.setLastName("Meier");
        c.setGender(Gender.MALE);        
        
        repo.persist(c);
        
        return c;
    }
    
}
