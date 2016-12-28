/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.interfaces;

import de.jreichl.jpa.entity.CompanyCustomer;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.entity.PrivateCustomer;
import de.jreichl.service.dto.CompanyCustomerDTO;
import de.jreichl.service.dto.PrivateCustomerDTO;
import java.util.List;

/**
 *
 * @author Jürgen Reichl
 */
public interface ICustomerService {
    
    public PrivateCustomer updatePrivateCustomer(PrivateCustomerDTO dto);
    
    public CompanyCustomer updateCompanyCustomer(CompanyCustomerDTO dto);
    
    public List<Customer> getCustomers();

    public void persistCustomer(Customer c);

    public Customer findCustomer(Customer customer);
    
}
