/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Creditworthiness;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.repository.CreditworthinessRepository;
import de.jreichl.service.interfaces.ICreditworthinessService;
import java.sql.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import net.poschinger.retailerposchinger.service.contractor.CreditWorthiness;


/**
 *
 * @author Jürgen Reichl
 */
@RequestScoped
public class CreditworthinessService implements ICreditworthinessService {
    
    @Inject
    private CreditworthinessRepository creditworthinessRepo;
    
    
    @Override
    public CreditWorthiness requestCreditworthiness(Customer customer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
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
                Logger.getLogger(getClass().getName()).log(Level.INFO, "Received CreditWorthiness from poschinger = "+c.name());
                return c;
            }
        } catch(Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Failed to get CreditworthinessServicePort", ex);
        }        
        */
        return null;
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
