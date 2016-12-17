/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.entity.Employee;
import de.jreichl.jpa.entity.type.TanType;
import de.jreichl.jpa.entity.util.EntityUtils;
import de.jreichl.jpa.repository.AccountRepository;
import de.jreichl.jpa.repository.EmployeeRepository;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Account service to create accounts for Customers
 * @author Jürgen Reichl
 */
@RequestScoped
public class AccountService {
    
    @Inject
    private AccountRepository accountRepo;
    
    @Inject
    private EmployeeRepository employeeRepo;
    
    /**
     * Create a new Account for an Customer. (account manager will be choosen by the bank)
     * @param owner The customer of the account
     * @param type Tan type for save transactions
     * @param password The password for the account
     * @return The new account
     */
    @Transactional
    public Account createAccount(Customer owner, TanType type, String password) {                
        List<Employee> employees = employeeRepo.findAll();
        Random r = new Random(System.currentTimeMillis());
        int randIndex = r.nextInt(employees.size());
        
        return createAccount(owner, employees.get(randIndex), type, password);
    }
    
    /**
     * Create a new Account for an Customer with an choosen account manager.
     * @param owner The customer of the account
     * @param accountManager The account maganger (an employee of the bank) for the new account
     * @param type Tan type for save transactions
     * @param password The password for the account
     * @return 
     */
    @Transactional
    public Account createAccount(Customer owner, Employee accountManager, TanType type, String password) {
        Account a = new Account();        
        
        a.setOwner(owner);
        a.setTanType(type);
        a.setAccountManager(accountManager);        
        String salt = EntityUtils.createRandomString(10);
        a.setPasswordSalt(salt);
        try {
            a.setHashedPassword(EntityUtils.hashPassword(password, salt));
        } catch (EntityUtils.EntityUtilException ex) {
            Logger.getLogger(AccountService.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
        
        a.setIban(createIBAN());
        
        accountRepo.persist(a);
        
        return a;
    }
    
    /**
     * Create a random unique IBAN
     * @return a new unique IBAN
     */
    @Transactional
    private String createIBAN() {   
        int tries = 0;
        while(true) {            
            String IBAN = EntityUtils.createRandomIBAN();

            Account a = null;
            try{
                tries++;
                a = accountRepo.findByIBAN(IBAN);
            } catch(Exception ex) {
                if(tries > 20)
                    throw ex;
            }
            if (a==null)
                return IBAN;
        }        
    }
    
}
