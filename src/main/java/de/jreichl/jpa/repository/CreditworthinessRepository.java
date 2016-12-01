/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.Creditworthiness;
import de.jreichl.jpa.entity.Customer;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jürgen Reichl
 */
@RequestScoped
public class CreditworthinessRepository extends SingleEntityRepository<Creditworthiness>{
    
    public CreditworthinessRepository() {
        super(Creditworthiness.class);
    }
    
    public List<Creditworthiness> findAllByCustomer(Customer customer) {        
        TypedQuery<Creditworthiness> query = em.createNamedQuery("Creditworthiness.Customer", Creditworthiness.class);
        query.setParameter("customer", customer);        
        return query.getResultList();
    }
    
}
