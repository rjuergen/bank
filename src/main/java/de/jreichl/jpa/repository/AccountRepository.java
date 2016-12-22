/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.Account;
import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jürgen Reichl
 */
@RequestScoped
public class AccountRepository extends SingleEntityRepository<Account> {
        
    
    public Account findByIBAN(String IBAN) {
        TypedQuery<Account> query = em.createNamedQuery("Account.IBAN", Account.class);
        query.setParameter("iban", IBAN.replace(" ", ""));        
        return query.getSingleResult();
    }
    
    public Long getHighestID() {
        Query query = em.createQuery("Select max(id) From Account");    
        Long id = 0L;
        try {
        Object o = query.getSingleResult();
        id = (Long) o;
        } catch(NoResultException ex) {
            // do nothing. it's just the first Account
        }
        return id;
    }
    
}
