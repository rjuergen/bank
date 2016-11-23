/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;

/**
 *
 * @author Jürgen Reichl
 */
@Entity
public class CompanyCustomer extends Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String name;
    
    private Date dateOfCreation;

    public CompanyCustomer() {
        
    }
    
    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }       
    
}
