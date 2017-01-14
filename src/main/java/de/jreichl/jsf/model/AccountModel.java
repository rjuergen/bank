/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jsf.model;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.AccountTransaction;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.entity.Employee;
import de.jreichl.jpa.entity.type.TanType;
import de.jreichl.service.BaseService;
import de.jreichl.service.interfaces.IAccountService;
import de.jreichl.service.interfaces.ICustomerService;
import java.io.Serializable;
import java.util.List;
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
public class AccountModel extends BaseService implements Serializable {
    private static final long serialVersionUID = 1L;    
    
    public class NewAccount{
        private Employee accountManager;
        private String password;
        private TanType tanType = TanType.SMS_TAN;
        public TanType getTanType() {
            return tanType;
        }
        public void setTanType(TanType tanType) {
            this.tanType = tanType;
        }
        public Employee getAccountManager() {
            return accountManager;
        }
        public void setAccountManager(Employee accountManager) {
            this.accountManager = accountManager;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }        
    }
    
    @Inject
    private IAccountService accountService;
    
    @Inject
    private ICustomerService customerService;
    
    @Inject
    private BankModel bankModel;
    
    private Customer customer = null;
   
    private List<Employee> employees;
    
    private NewAccount account = new NewAccount();
    
    public void save() {        
        Account a = accountService.createAccount(customer, account.getAccountManager(), account.getTanType(), account.getPassword());
        if(a != null)
            customer = customerService.findCustomer(a.getOwner());
        clear();
    }
    
    public void clear() {
        account = new NewAccount();
    }

    public NewAccount getAccount() {
        return account;
    }

    public void setAccount(NewAccount account) {
        this.account = account;
    }    
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }  
    
    public List<Employee> getEmployees() {
        if(employees==null)
            employees = accountService.getAccountManager();
        return employees;
    }
    
    public String getAssociatedAccount(AccountTransaction at) {
        StringBuilder sb = new StringBuilder();
        try{
            if(at.getAssociatedTransaction() != null) {      
                Account a = at.getAssociatedTransaction().getAccount();
                if(a.getOwner() != null)
                    sb.append(a.getOwner().getName());
                else
                    sb.append(bankModel.getBank().getName());
                sb.append(" [").append(a.getIban()).append("]\n");
            }
        } catch(Exception ex) {
            logger.log(Level.WARNING, "Failed to get AssociatedTransaction");
        }
        return sb.toString();
    }

}
