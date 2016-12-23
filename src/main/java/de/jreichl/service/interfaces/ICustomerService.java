/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.interfaces;

import de.jreichl.jpa.entity.CompanyCustomer;
import de.jreichl.jpa.entity.PrivateCustomer;
import de.jreichl.jpa.entity.type.Gender;
import de.jreichl.service.dto.AddressDTO;
import java.util.Date;

/**
 *
 * @author Jürgen Reichl
 */
public interface ICustomerService {
    
    public PrivateCustomer createPrivateCustomer(String firstName, String lastName, Gender gender, Date dateOfBirth, AddressDTO address);
    
    public CompanyCustomer createCompanyCustomer(String name, Date dateOfCreation, AddressDTO address);
    
}
