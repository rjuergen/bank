/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jsf.model;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.service.interfaces.IAccountService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Jürgen Reichl
 */
@Named
@SessionScoped
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String accountNumber;
    
    private String passwort;
    
    private Account currentAccount;
    
    @Inject
    private IAccountService accountService;
    
    public boolean hasActiveUser() {
        return currentAccount != null;
    }
    
    public void login() {
        currentAccount = accountService.login(accountNumber, passwort);
    }
    
    public void logout() {
        currentAccount = null;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public Customer getCurrentUser() {
        return currentAccount.getOwner();
    }   
    
}
