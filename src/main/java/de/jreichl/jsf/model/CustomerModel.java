/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jsf.model;

import de.jreichl.jpa.entity.type.Gender;
import de.jreichl.service.CustomerService;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;


/**
 *
 * @author Jürgen Reichl
 */
@Named
@SessionScoped
public class CustomerModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private CustomerService customerService;
    
    private String firstName;    
    private String lastName;    
    private Gender gender;    
    private Date dateOfBirth;
    
    private String houseNr;    
    private String street;
    private String zip;
    private String city;
    
    public void CreatePrivateCustomer() {        
        Logger.getLogger(getClass().getName()).log(Level.INFO, String.format("Creating private customer! firstName=%s lastName=%s",firstName, lastName));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    
}
