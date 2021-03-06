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
    
    private String bankCode;
    
    private String bic;
    
    private String countryAlpha2Code;
    
    @OneToOne
    private Account creditAccount;

    @OneToOne
    private Account salaryAccount;
        
    
    public Bank() {
        
    }
    
    public Bank(String bankCode, String bic, String countryAlpha2Code, String name, Address address) {
        this.bankCode = bankCode;
        this.bic = bic;
        this.countryAlpha2Code = countryAlpha2Code;
        this.name = name;
        this.address = address;        
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getCountryAlpha2Code() {
        return countryAlpha2Code;
    }

    public void setCountryAlpha2Code(String countryAlpha2Code) {
        this.countryAlpha2Code = countryAlpha2Code;
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
