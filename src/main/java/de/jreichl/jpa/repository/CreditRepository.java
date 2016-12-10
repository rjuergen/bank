/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.Credit;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jürgen Reichl
 */
@RequestScoped
public class CreditRepository extends SingleEntityRepository<Credit> {
    
    public CreditRepository() {
        super(Credit.class);
    }
    
    public List<Credit> findAllUnpaid() {        
        TypedQuery<Credit> query = em.createNamedQuery("Credit.unpaid", Credit.class);               
        return query.getResultList();
    }

}
