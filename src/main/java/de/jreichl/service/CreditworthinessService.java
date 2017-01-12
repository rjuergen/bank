/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.common.DateTimeUtil;
import de.jreichl.jpa.entity.Creditworthiness;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.entity.PrivateCustomer;
import de.jreichl.jpa.repository.CreditworthinessRepository;
import de.jreichl.service.biz.CreditworthinessCalulator;
import de.jreichl.service.interfaces.ICreditworthinessService;
import de.jreichl.service.interfaces.ICustomerService;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import net.poschinger.retailerposchinger.service.contractor.webservice.CreditWorthiness;
import net.poschinger.retailerposchinger.service.contractor.webservice.CreditworthinessServiceService;




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
        
        net.poschinger.retailerposchinger.service.contractor.webservice.CreditWorthiness extCreditWorthiness = requestCreditworthinessFromRetailerPoschinger(customer);             
        
        long possibleCredit = CreditworthinessCalulator.calculate(extCreditWorthiness, customer);
        
        Creditworthiness c = creditworthinessRepo.create(customer, possibleCredit); 
        
        return c;        
    }    
    
    @Override
    public CreditWorthiness requestCreditworthinessFromRetailerPoschinger(Customer customer) {
        if(!(customer instanceof PrivateCustomer))
            return CreditWorthiness.NODATA;
        PrivateCustomer pc = (PrivateCustomer) customer;
        try{            
            CreditworthinessServiceService poschingerService = new CreditworthinessServiceService();
            net.poschinger.retailerposchinger.service.contractor.webservice.CreditworthinessService port = poschingerService.getCreditworthinessServicePort();            
            CreditWorthiness c = port.getCreditWorthiness(pc.getFirstName(), pc.getLastName(), null, null, null);
            if(c != null) {
                logger.log(Level.INFO, "Received CreditWorthiness from poschinger: "+c.name());
                return c;
            }
        } catch(Exception ex) {
            logger.log(Level.SEVERE, "Failed to get CreditworthinessServicePort", ex);
        }       
        
        return CreditWorthiness.NODATA;
    }
    
}
