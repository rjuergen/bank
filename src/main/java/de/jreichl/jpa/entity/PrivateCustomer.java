/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import de.jreichl.jpa.entity.embeddable.Address;
import de.jreichl.jpa.entity.type.Gender;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;

/**
 *
 * @author Jürgen Reichl
 */
@Entity
public class PrivateCustomer extends Customer implements Serializable {

    private static final long serialVersionUID = 1L;    

    private String firstName;
    
    private String lastName;
    
    private Gender gender;
    
    private Date dateOfBirth;

    public PrivateCustomer() {
        
    }

    public PrivateCustomer(String firstName, String lastName, Gender gender, Date dateOfBirth, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
    
    @Override
    public String getName() {
        return new StringBuilder(firstName).append(" ").append(lastName).toString();
    }   
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
    
}
