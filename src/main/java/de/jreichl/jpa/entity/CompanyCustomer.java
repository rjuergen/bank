/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author Jürgen Reichl
 */
@Entity
public class CompanyCustomer extends Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String name;

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return name;
    }       
    
    @Override
    public String toString() {
        return "de.jreichl.jpa.entity.CompanyCustomer[ id=" + getId() + " ]";
    }
    
}
