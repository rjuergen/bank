/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import de.jreichl.jpa.entity.embeddable.Address;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Jürgen Reichl
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Customer extends SingleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
       
    @Embedded
    protected Address address;

    @OneToMany(mappedBy = "owner")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Account> accounts = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Credit> credits = new ArrayList<>();
        
    @OneToMany(mappedBy = "customer")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Creditworthiness> creditworthinesses = new ArrayList<>();
    
    public Customer() {
        
    }
    
    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        account.setOwner(this);
        accounts.add(account);
    }
    
    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public List<Creditworthiness> getCreditworthinesses() {
        return creditworthinesses;
    }

    public void setCreditworthinesses(List<Creditworthiness> creditworthinesses) {
        this.creditworthinesses = creditworthinesses;
    }    
    
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }    
        
    public abstract String getName();

    public void addCredit(Credit c) {
        credits.add(c);
        c.setCustomer(this);
    }
   
    public void addCreditworthiness(Creditworthiness c) {
        creditworthinesses.add(c);
        c.setCustomer(this);
    }
  
}
