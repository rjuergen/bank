/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.Account;
import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jürgen Reichl
 */
@RequestScoped
public class AccountRepository extends SingleEntityRepository<Account> {
    
    public AccountRepository() {
        super(Account.class);
    }
        
    
    public Account findByIBAN(String IBAN) {
        TypedQuery<Account> query = em.createNamedQuery("Account.IBAN", Account.class);
        query.setParameter("iban", IBAN.replace(" ", ""));        
        return query.getSingleResult();
    }
    
}
