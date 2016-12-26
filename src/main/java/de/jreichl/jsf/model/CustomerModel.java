/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jsf.model;

import de.jreichl.jpa.entity.Customer;
import de.jreichl.jsf.dto.CompanyCustomerDTO;
import de.jreichl.jsf.dto.PrivateCustomerDTO;
import de.jreichl.service.BaseService;
import de.jreichl.service.CustomerService;
import de.jreichl.service.dto.AddressDTO;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;


/**
 *
 * @author Jürgen Reichl
 */
@Named
@SessionScoped
public class CustomerModel extends BaseService implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private CustomerService customerService;
    
    private List<Customer> customers;
    
    private PrivateCustomerDTO privateCustomer;     
    private CompanyCustomerDTO companyCustomer;
    private AddressDTO address;
        
    public List<Customer> getCustomers() {
        if(customers == null) {
            customers = customerService.getCustomers();
        }
        return customers;
    }    
    
    public void select(Customer c) {
        
    }
    
    public void create() {        
        Customer newOne = null;
        if(privateCustomer != null) {
            newOne = customerService.createPrivateCustomer(privateCustomer.getFirstName(), 
                    privateCustomer.getLastName(), privateCustomer.getGender(), 
                    privateCustomer.getDateOfBirth(), address);
        } else if(companyCustomer != null) {
            newOne = customerService.createCompanyCustomer(companyCustomer.getName(), 
                    companyCustomer.getDateOfCreation(), address);
        }
        privateCustomer = null;    
        companyCustomer = null;
        address = null;
        customers.set(0, newOne);
    }
    
    public void newPrivateCustomer() {
        privateCustomer = new PrivateCustomerDTO();
        address = new AddressDTO();
    }
    
    public void newCompanyCustomer() {
        companyCustomer = new CompanyCustomerDTO();
        address = new AddressDTO();
    }

    public boolean isCreating() {
        return privateCustomer != null || companyCustomer != null;
    }
    
    public PrivateCustomerDTO getPrivateCustomer() {
        return privateCustomer;
    }

    public void setPrivateCustomer(PrivateCustomerDTO privateCustomer) {
        this.privateCustomer = privateCustomer;
    }

    public CompanyCustomerDTO getCompanyCustomer() {
        return companyCustomer;
    }

    public void setCompanyCustomer(CompanyCustomerDTO companyCustomer) {
        this.companyCustomer = companyCustomer;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }    
}
