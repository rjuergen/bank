/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Creditworthiness;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.service.interfaces.ICreditworthinessService;
import java.util.Random;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import net.poschinger.retailerposchinger.service.contractor.CreditWorthiness;

/**
 *
 * @author Jürgen Reichl
 */
@Alternative
@RequestScoped
public class CreditworthinessTestService extends BaseService implements ICreditworthinessService{

    private static final Random random = new Random(System.currentTimeMillis());
    
    @Inject
    private CreditworthinessService originalService;
    
    @Override
    public Creditworthiness requestCreditworthiness(Customer customer) {
        return originalService.requestCreditworthiness(customer);
    }

    @Override
    public CreditWorthiness requestCreditworthinessFromRetailerPoschinger(Customer customer) {
        // return a random value
        CreditWorthiness[] values = CreditWorthiness.values();
        return values[random.nextInt(values.length)];        
    }
    
}
