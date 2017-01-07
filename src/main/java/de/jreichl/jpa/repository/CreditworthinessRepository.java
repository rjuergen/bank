/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.Creditworthiness;
import de.jreichl.jpa.entity.Customer;
import java.sql.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

/**
 *
 * @author Jürgen Reichl
 */
@RequestScoped
public class CreditworthinessRepository extends SingleEntityRepository<Creditworthiness>{
  
    public List<Creditworthiness> findAllByCustomer(Customer customer) {        
        TypedQuery<Creditworthiness> query = em.createNamedQuery("Creditworthiness.Customer", Creditworthiness.class);
        query.setParameter("customer", customer);        
        return query.getResultList();
    }
    
    @Transactional
    public Creditworthiness create(Customer customer, long possibleCredit) {
        customer = em.find(customer.getClass(), customer.getId());        
        
        Creditworthiness c = new Creditworthiness();
        c.setCreationDate(new Date(new java.util.Date().getTime()));        
        c.setPossibleCredit(possibleCredit);
        customer.addCreditworthiness(c);
        
        em.persist(c);
        em.persist(customer);
        
        return c;
    }
    
}
