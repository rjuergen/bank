/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Creditworthiness;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.repository.CreditworthinessRepository;
import java.sql.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;


/**
 *
 * @author Jürgen Reichl
 */
@RequestScoped
public class CreditworthinessService {
    
    @Inject
    private CreditworthinessRepository creditworthinessRepo;
    
    
    public void requestCreditworthinessFromRetailerPoschinger(Customer customer) {
        
    }
    
    @Transactional
    private Creditworthiness createCreditworthiness(Customer customer, long possibleCredit) {
        Creditworthiness c = new Creditworthiness();
        c.setCreationDate(new Date(new java.util.Date().getTime()));
        c.setCustomer(customer);
        c.setPossibleCredit(possibleCredit);
        
        creditworthinessRepo.persist(c);
        
        return c;
    }
    
}
