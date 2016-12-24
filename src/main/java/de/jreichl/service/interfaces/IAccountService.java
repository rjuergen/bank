/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.interfaces;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.entity.Employee;
import de.jreichl.jpa.entity.type.TanType;
import java.util.List;

/**
 *
 * @author Jürgen Reichl
 */
public interface IAccountService {
    
    public Account createAccount(Customer owner, Employee accountManager, TanType type, String password);
    
    public Account createAccount(Customer owner, TanType type, String password);
    
    public boolean deleteAccount(Account toDelete);
    
    public List<Account> getAccounts(Customer owner);
    
}