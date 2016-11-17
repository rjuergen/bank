/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import de.jreichl.jpa.entity.embeddable.Address;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 *
 * @author Jürgen Reichl
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Customer extends SingleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Embedded
    private Address address;

    @OneToMany
    private List<Account> accounts;

    public Customer() {
        
    }
    
    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    
    
    
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }    
        
    public abstract String getName();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }  

    @Override
    public String toString() {
        return "de.jreichl.jpa.entity.Customer[ id=" + id + " ]";
    }
    
}
