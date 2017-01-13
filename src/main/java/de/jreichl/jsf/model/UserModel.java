/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jsf.model;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.service.BaseService;
import de.jreichl.service.exception.LoginFailedException;
import de.jreichl.service.interfaces.IAccountService;
import java.io.Serializable;
import java.util.logging.Level;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Jürgen Reichl
 */
@Named
@SessionScoped
public class UserModel extends BaseService implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String accountNumber;
    
    private String passwort;
    
    private Account currentAccount;
    
    private String homeTitel = "";    
    
    private String message;
    
    @Inject
    private IAccountService accountService;
    
    @Inject
    private CreditModel creditModel;
    @Inject
    private StandingOrderModel standingOrderModel;
    @Inject
    private TransactionModel transactionModel;
    
    public boolean hasActiveUser() {
        return currentAccount != null;
    }
    
    public void login() {
        try {
            currentAccount = accountService.login(accountNumber, passwort);
            homeTitel = "Transaktionen von " + getCurrentUser().getName();
            accountNumber =  null;
            passwort = null;
            message = null;
        } catch (LoginFailedException ex) {
            currentAccount = null;
            passwort = null;
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            message = ex.getMessage();
        }
    }
    
    public void logout() {
        currentAccount = null;
        homeTitel = "";   
        message = null;
        creditModel.clear();
        standingOrderModel.clear();
        transactionModel.clear();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getMessage() {
        return message;
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

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    public String getHomeTitel() {
        return homeTitel;
    }

    public void setHomeTitel(String homeTitel) {
        this.homeTitel = homeTitel;
    }
    
    void refresh() {
        currentAccount = accountService.findAccount(currentAccount.getId());
    }   
        
}
