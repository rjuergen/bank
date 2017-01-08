/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.test;

import de.jreichl.common.DateTimeUtil;
import de.jreichl.jpa.entity.Creditworthiness;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.repository.CreditworthinessRepository;
import de.jreichl.service.BaseService;
import de.jreichl.service.biz.CreditworthinessCalulator;
import de.jreichl.service.interfaces.ICreditworthinessService;
import de.jreichl.service.interfaces.ICustomerService;
import java.util.Random;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.transaction.Transactional;
import net.poschinger.retailerposchinger.service.contractor.webservice.CreditWorthiness;

/**
 *
 * @author Jürgen Reichl
 */
@Alternative
@RequestScoped
public class CreditworthinessTestService extends BaseService implements ICreditworthinessService {

    private static final Random random = new Random(System.currentTimeMillis());
    
    @Inject
    private CreditworthinessRepository creditworthinessRepo;
    
    @Inject
    private ICustomerService customerService;
    
    @Transactional
    @Override
    public Creditworthiness requestCreditworthiness(Customer customer) {    
        logger.log(Level.INFO, "runngin test version of requestCreditworthiness.");
        customer = customerService.findCustomer(customer);
        for (Creditworthiness c : customer.getCreditworthinesses()) {
            if(DateTimeUtil.isToday(c.getCreationDate())) {      
                // found a creditworthiness for today!
                logger.log(Level.INFO, "Found a creditworthiness from today.");
                return c;
            }
        }
         
        // no creditworthiness for today.. request a new one
        
        CreditWorthiness extCreditWorthiness = requestCreditworthinessFromRetailerPoschinger(customer);
        
        if(extCreditWorthiness!=null)
            logger.log(Level.INFO, "Received CreditWorthiness from RetailerPoschinger: {0}", extCreditWorthiness.name());
        else
            logger.log(Level.WARNING, "No CreditWorthiness from RetailerPoschinger received!");
        
        long possibleCredit = CreditworthinessCalulator.calculate(extCreditWorthiness, customer);
        
        Creditworthiness c = creditworthinessRepo.create(customer, possibleCredit);        
        
        
        
        return c;        
    }

    @Override
    public CreditWorthiness requestCreditworthinessFromRetailerPoschinger(Customer customer) {
        logger.log(Level.INFO, "runngin test version of requestCreditworthinessFromRetailerPoschinger.");
        // return a random value
        CreditWorthiness[] values = CreditWorthiness.values();
        return values[random.nextInt(values.length)];        
    }    

}
