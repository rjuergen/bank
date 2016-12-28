/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Jürgen Reichl
 */
public class CompanyCustomerDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id = null;
    private String name;
    private Date dateOfCreation;               
    private AddressDTO address;
    
    public boolean isNew() {
        return id==null;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }  
}
