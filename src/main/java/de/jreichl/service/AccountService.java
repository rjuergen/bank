/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.entity.Employee;
import de.jreichl.jpa.entity.type.TanType;
import de.jreichl.jpa.repository.AccountRepository;
import de.jreichl.jpa.repository.EmployeeRepository;
import java.util.List;
import java.util.Random;
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
     * @return The new account
     */
    @Transactional
    public Account createAccount(Customer owner, TanType type) {                
        List<Employee> employees = employeeRepo.findAll();
        Random r = new Random(System.currentTimeMillis());
        int randIndex = r.nextInt(employees.size());
        
        return createAccount(owner, employees.get(randIndex), type);
    }
    
    /**
     * Create a new Account for an Customer with an choosen account manager.
     * @param owner The customer of the account
     * @param accountManager The account maganger (an employee of the bank) for the new account
     * @param type Tan type for save transactions
     * @return 
     */
    @Transactional
    public Account createAccount(Customer owner, Employee accountManager, TanType type) {
        Account a = new Account();        
        
        a.setOwner(owner);
        a.setTanType(type);
        a.setAccountManager(accountManager);        
        
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
        while(true) {
            Random r = new Random(System.currentTimeMillis());
            StringBuilder iban = new StringBuilder("DE");
            for(int i = 0; i<20; i++)
                iban.append(r.nextInt(10));

            String IBAN = iban.toString();

            Account a = null;
            try{
                a = accountRepo.findByIBAN(IBAN);
            } catch(Exception ex) {
            }
            if (a==null)
                return IBAN;
        }        
    }
    
}
