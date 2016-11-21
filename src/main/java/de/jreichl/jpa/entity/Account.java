/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import de.jreichl.jpa.entity.type.TanType;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Jürgen Reichl
 */
@Entity
public class Account extends SingleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String iban;
        
    private String accountNr;
    
    @ManyToOne
    private Customer owner;
    
    @ManyToOne
    private Employee accountManager;
    
    private TanType tanType;
    
    @OneToMany(mappedBy = "account")
    private List<AccountTransaction> transactions;

    public Account() {
        
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

    public String getAccountNr() {
        return accountNr;
    }

    public void setAccountNr(String accountNr) {
        this.accountNr = accountNr;
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
        return transactions;
    }

    public void setTransactions(List<AccountTransaction> transactions) {
        this.transactions = transactions;
    } 
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "de.jreichl.jpa.entity.Account[ id=" + id + " ]";
    }
    
}
