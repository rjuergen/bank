/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import de.jreichl.jpa.entity.embeddable.Address;
import java.io.Serializable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author Jürgen Reichl
 */
@Entity
public class Bank extends SingleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Embedded
    private Address address;
    
    private String name;
    
    private String bic;
    
    @OneToOne
    private Account creditAccount;

    @OneToOne
    private Account salaryAccount;
    
    public Bank() {
        
    }
    
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }   
    
    public Account getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(Account creditAccount) {
        this.creditAccount = creditAccount;
    }

    public Account getSalaryAccount() {
        return salaryAccount;
    }

    public void setSalaryAccount(Account salaryAccount) {
        this.salaryAccount = salaryAccount;
    }

    
    
}
