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
import java.util.Random;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author Jürgen Reichl
 */
public class AccountService {
    
    @Inject
    private AccountRepository accountRepository;
    
    @Transactional
    public Account createAccount(Customer owner, Employee accountManager, TanType type) {
        Account a = new Account();        
        
        a.setOwner(owner);
        a.setTanType(type);
        a.setAccountManager(accountManager);        
        
        a.setIban(createIBAN());
        
        accountRepository.persist(a);
        
        return a;
    }
    
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
                a = accountRepository.findByIBAN(IBAN);
            } catch(Exception ex) {
            }
            if (a==null)
                return IBAN;
        }        
    }
    
}
