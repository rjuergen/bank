/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import de.jreichl.jpa.entity.type.TanType;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Jürgen Reichl
 */
@Entity
@NamedQueries({
    @NamedQuery(name="Account.IBAN",query="SELECT a FROM Account a WHERE a.iban = :iban"),
    @NamedQuery(name="Account.AccountNumber",query="SELECT a FROM Account a WHERE a.accountNumber = :accountNumber")
})
public class Account extends SingleEntity implements Serializable {

    private static final long serialVersionUID = 1L;    

    @Column(unique=true)
    private String accountNumber;
    
    @Column(unique=true)
    private String iban;
    
    @ManyToOne
    private Customer owner;
    
    @ManyToOne
    private Employee accountManager;
    
    private TanType tanType;
    
    private Date dateOfCreation;
    
    private String hashedPassword;
    
    private String passwordSalt;
    
    @OneToMany(mappedBy = "account")
    private List<AccountTransaction> transactions = new ArrayList<>();
    
    @OneToMany(mappedBy = "fromAccount")
    private List<StandingOrder> standingOrders = new ArrayList<>();   
    
    public Account() {
        
    }

    public Account(String accountNumber, String iban, Employee accountManager, TanType tanType, Date dateOfCreation, String passwordSalt, String hashedPassword) {
        this.accountNumber = accountNumber;
        this.iban = iban;
        this.accountManager = accountManager;
        this.tanType = tanType;
        this.dateOfCreation = dateOfCreation;
        this.passwordSalt = passwordSalt;
        this.hashedPassword = hashedPassword;
    }

    public void addStandingOrder(StandingOrder order) {
        order.setFromAccount(this);
        standingOrders.add(order);
    }
    
    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }  

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    
    
    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List<StandingOrder> getStandingOrders() {
        return Collections.unmodifiableList(standingOrders);
    }    
    
    public TanType getTanType() {
        return tanType;
    }

    public void setTanType(TanType tanType) {
        this.tanType = tanType;
    }
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Employee getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(Employee accountManager) {
        this.accountManager = accountManager;
    }

    public List<AccountTransaction> getTransactions() {        
        return Collections.unmodifiableList(transactions);
    }  
    
    public List<AccountTransaction> getTransactionsSorted() {      
        transactions.sort(new Comparator<AccountTransaction>() {
            @Override
            public int compare(AccountTransaction o1, AccountTransaction o2) {
                return o1.getTransactionDate().compareTo(o2.getTransactionDate());
            }
        });
        return Collections.unmodifiableList(transactions);
    }  
   
}
