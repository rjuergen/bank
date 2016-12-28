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
    
    public Account findByAccountNumber(String accountNumber) {
        TypedQuery<Account> query = em.createNamedQuery("Account.AccountNumber", Account.class);
        query.setParameter("accountNumber", accountNumber);        
        return query.getSingleResult();
    }
    
    public Long getHighestID() {
        Query query = em.createQuery("Select max(a.id) From Account a");    
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
