/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import de.jreichl.jpa.entity.type.TanType;
import java.io.Serializable;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Jürgen Reichl
 */
@Entity
@NamedQuery(name="Account.IBAN",query="SELECT a FROM Account a WHERE a.iban = :iban")
public class Account extends SingleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String iban;
    
    @ManyToOne
    private Customer owner;
    
    @ManyToOne
    private Employee accountManager;
    
    private TanType tanType;
    
    private Date dateOfCreation;
    
    @OneToMany(mappedBy = "account")
    private List<AccountTransaction> transactions;
    
    @OneToMany(mappedBy = "fromAccount")
    private List<StandingOrder> standingOrders;   
    
    public Account() {
        
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
    
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
   
}
