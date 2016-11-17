/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.service;

import de.jreichl.jpa.entity.PrivateCustomer;
import de.jreichl.jpa.entity.type.Gender;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Jürgen Reichl
 */
public class AccountService {
    
    @PersistenceContext
    private EntityManager entityManager;
 
    @Transactional
    public PrivateCustomer createDummyPrivateCustomer() {
        // PrivateCustomer (entity) anlegen (im Heap)
        PrivateCustomer c = new PrivateCustomer();
        c.setFirstName("Hans");
        c.setLastName("Meier");
        c.setGender(Gender.MALE);        
        
        entityManager.persist(c);
        
        return c;
    }
    
}
