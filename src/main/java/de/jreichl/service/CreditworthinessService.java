/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Creditworthiness;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.repository.CreditworthinessRepository;
import de.jreichl.service.interfaces.ICreditworthinessService;
import de.jreichl.service.interfaces.ICustomerService;
import java.sql.Date;
import java.util.Calendar;
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
        Calendar today = Calendar.getInstance();
        today.setTime(new java.util.Date());
        today.set(0, 0, 0);
        for (Creditworthiness c : customer.getCreditworthinesses()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(c.getCreationDate());
            if(isSameDate(today, cal)) {      
                // found a creditworthiness for today!
                return c;
            }
        }
         
        // no creditworthiness for today.. request a new one
        
        CreditWorthiness extCreditWorthiness = requestCreditworthinessFromRetailerPoschinger(customer);
        
        // TODO add logic
        
        Creditworthiness c = createCreditworthiness(customer, 100000);        
        
        
        
        return c;        
    }
    
    private boolean isSameDate(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && 
            c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
            c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
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
    
    @Transactional
    private Creditworthiness createCreditworthiness(Customer customer, long possibleCredit) {
        customer = customerService.findCustomer(customer);
        
        Creditworthiness c = new Creditworthiness();
        c.setCreationDate(new Date(new java.util.Date().getTime()));        
        c.setPossibleCredit(possibleCredit);
        customer.addCreditworthiness(c);
        
        creditworthinessRepo.persist(c);
        customerService.persistCustomer(customer);
        
        return c;
    }
    
}
