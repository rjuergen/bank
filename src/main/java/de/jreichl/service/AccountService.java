/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.Bank;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.entity.Employee;
import de.jreichl.jpa.entity.type.TanType;
import de.jreichl.jpa.entity.util.EntityUtils;
import de.jreichl.jpa.repository.AccountRepository;
import de.jreichl.jpa.repository.BankRepository;
import de.jreichl.jpa.repository.EmployeeRepository;
import de.jreichl.service.interfaces.IAccountService;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.iban4j.CountryCode;
import org.iban4j.Iban;

/**
 * Account service to create accounts for Customers
 * @author Jürgen Reichl
 */
@RequestScoped
public class AccountService extends BaseService implements IAccountService { 
    
    @Inject
    private AccountRepository accountRepo;
    
    @Inject
    private EmployeeRepository employeeRepo;
    
    @Inject 
    private BankRepository bankRepo;
    
    /**
     * Create a new Account for an Customer. (account manager will be choosen by the bank)
     * @param owner The customer of the account
     * @param type Tan type for save transactions
     * @param password The password for the account
     * @return The new account
     */
    @Transactional
    @Override
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
    @Override
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
            logger.log(Level.SEVERE, "Failed to hash password!", ex);
            throw new RuntimeException(ex);
        }
        
        String accountNumber = EntityUtils.createAccountNumber(accountRepo.getHighestID());
        a.setAccountNumber(accountNumber);
        
        Bank bank = bankRepo.getBank();
        
        Iban iban = new Iban.Builder()
                .countryCode(CountryCode.getByCode(bank.getCountryAlpha2Code()))
                .bankCode(bank.getBankCode())
                .accountNumber(accountNumber)
                .build();
        
        a.setIban(iban.toString());
        
        accountRepo.persist(a);
        
        return a;
    }

    @Override
    public boolean deleteAccount(Account toDelete) {
        accountRepo.remove(toDelete);
        logger.log(Level.INFO, String.format("Account %s deleted!",toDelete.getAccountNumber()));
        return true;
    }

    @Override
    public Account login(String accountNumber, String password) throws IllegalArgumentException {
        Account a = accountRepo.findByAccountNumber(accountNumber);
        String hashedPW = null;
        try {
            hashedPW = EntityUtils.hashPassword(password, a.getPasswordSalt());
        } catch (EntityUtils.EntityUtilException ex) {
            logger.log(Level.SEVERE, "Failed to hash password!", ex);
            throw new RuntimeException(ex);
        }
        if(!a.getHashedPassword().equals(hashedPW)) {            
            throw new IllegalArgumentException("Password is not correct!");
        }        
        return a;
    }
    
}
