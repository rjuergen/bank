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
import de.jreichl.service.exception.LoginFailedException;
import de.jreichl.service.interfaces.IAccountService;
import de.jreichl.service.interfaces.ICustomerService;
import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
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
    
    @Inject
    private ICustomerService customerService;
    
    private static final Random random = new Random(System.currentTimeMillis());
    
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
        return createAccount(owner, null, type, password);
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
        
        a.setDateOfCreation(new Date(new java.util.Date().getTime()));
        a.setTanType(type);
        
        if(accountManager == null) {
            List<Employee> employees = employeeRepo.findAll();            
            int randIndex = random.nextInt(employees.size());        
            accountManager = employees.get(randIndex);
        } else {
            accountManager = employeeRepo.findById(accountManager.getId());
        }
        a.setAccountManager(accountManager);       
        
        String salt = EntityUtils.createRandomString(10);
        a.setPasswordSalt(salt);
        try {
            a.setHashedPassword(EntityUtils.hashPassword(password, salt));
        } catch (EntityUtils.EntityUtilException ex) {
            logger.log(Level.SEVERE, "Failed to hash password!", ex);
            throw new RuntimeException(ex);
        }
        long highestID = accountRepo.getHighestID();
        String accountNumber = EntityUtils.createAccountNumber(highestID);
        a.setAccountNumber(accountNumber);
        Bank bank = bankRepo.getBank();
        
        Iban iban = new Iban.Builder()
                .countryCode(CountryCode.getByCode(bank.getCountryAlpha2Code()))
                .bankCode(bank.getBankCode())
                .accountNumber(accountNumber)
                .build();
        
        a.setIban(iban.toString());
                
        owner = customerService.findCustomer(owner);
        
        owner.addAccount(a);
                
        accountRepo.persist(a);
                
        return a;
    }

    @Transactional
    @Override
    public boolean deleteAccount(Account toDelete) {
        toDelete = accountRepo.merge(toDelete);
        accountRepo.remove(toDelete);
        logger.log(Level.INFO, String.format("Account %s deleted!",toDelete.getAccountNumber()));
        return true;
    }

    @Override
    public Account login(String accountNumber, String password) throws LoginFailedException {
        try{
            Account a = accountRepo.findByAccountNumber(accountNumber);
        
            String hashedPW = EntityUtils.hashPassword(password, a.getPasswordSalt());
            if(!a.getHashedPassword().equals(hashedPW)) {            
                throw new LoginFailedException(accountNumber, "Passwort nicht korrekt!");
            }     
            return a;
        } catch (EntityUtils.EntityUtilException ex) {
            logger.log(Level.SEVERE, "Failed to hash password!", ex);
            throw new LoginFailedException(accountNumber, "Login aus unbekannten Gründen fehlgeschlagen!", ex);
        } catch (NoResultException ex) {
            logger.log(Level.SEVERE, "Failed to find Account!", ex);
            throw new LoginFailedException(accountNumber, String.format("Kontonummer %s nicht korrekt!",accountNumber), ex);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to login!", ex);
            throw new LoginFailedException(accountNumber, "Login aus unbekannten Gründen fehlgeschlagen!", ex);
        }
    }

    @Override
    public List<Employee> getAccountManager() {
        return employeeRepo.findAll();
    }

    @Override
    public Account findAccount(long id) {
        return accountRepo.findById(id);
    }

    @Override
    public Account findAccount(String iban) throws NoResultException {
        return accountRepo.findByIBAN(iban);
    }
    
}
