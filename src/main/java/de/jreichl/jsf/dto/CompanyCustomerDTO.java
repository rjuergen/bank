/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jsf.dto;

import java.util.Date;

/**
 *
 * @author Jürgen Reichl
 */
public class CompanyCustomerDTO {
    private String name;
    private Date dateOfCreation;               

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
