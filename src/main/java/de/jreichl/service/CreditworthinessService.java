/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.common.DateTimeUtil;
import de.jreichl.jpa.entity.Creditworthiness;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.repository.CreditworthinessRepository;
import de.jreichl.service.interfaces.ICreditworthinessService;
import de.jreichl.service.interfaces.ICustomerService;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import net.poschinger.retailerposchinger.service.contractor.CreditWorthiness;



/**
 *
 * @author Jürgen Reichl
 */
@RequestScoped
public class CreditworthinessService extends BaseService implements ICreditworthinessService {
    
    @Inject
    private CreditworthinessRepository creditworthinessRepo;
    
    @Inject
    private ICustomerService customerService;
    
    @Transactional
    @Override
    public Creditworthiness requestCreditworthiness(Customer customer) {        
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
            logger.log(Level.INFO, "Received CreditWorthiness from RetailerPoschinger: "+extCreditWorthiness.name());
        else
            logger.log(Level.WARNING, "No CreditWorthiness from RetailerPoschinger received!");
        // TODO add logic
        
        Creditworthiness c = creditworthinessRepo.create(customer, 100000);        
        
        
        
        return c;        
    }    
    
    @Override
    public CreditWorthiness requestCreditworthinessFromRetailerPoschinger(Customer customer) {
        /*
        try{
            CreditworthinessServiceService poschingerService = new CreditworthinessServiceService();
            net.poschinger.retailerposchinger.service.contractor.CreditworthinessService port = poschingerService.getCreditworthinessServicePort();
            CreditWorthiness c = port.getCreditWorthiness(customer.getFirstName(), customer.getLastName(), 
                    customer.getAddress().getStreet()+" "+customer.getAddress().getHouseNr(), 
                    customer.getAddress().getZip(), customer.getAddress().getCountry());
            if(c != null) {
                logger.log(Level.INFO, "Received CreditWorthiness from poschinger = "+c.name());
                return c;
            }
        } catch(Exception ex) {
            logger.log(Level.SEVERE, "Failed to get CreditworthinessServicePort", ex);
        }        
        */
        return null;
    }
    
}
