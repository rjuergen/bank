/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jsf.model;

import de.jreichl.jpa.entity.CompanyCustomer;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.entity.PrivateCustomer;
import de.jreichl.service.BaseService;
import de.jreichl.service.dto.AddressDTO;
import de.jreichl.service.dto.CompanyCustomerDTO;
import de.jreichl.service.dto.PrivateCustomerDTO;
import de.jreichl.service.interfaces.ICustomerService;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
    private ICustomerService customerService;
    
    @Inject
    private AccountModel accountModel;
    
    private Set<Customer> customers;
    
    /**
     * 0 = PrivateCustomer;
     * 1 = CompanyCustomer
     */
    private int activeTab = 0;
    
    private PrivateCustomerDTO privateCustomer = new PrivateCustomerDTO();     
    private CompanyCustomerDTO companyCustomer = new CompanyCustomerDTO();
        
    public Set<Customer> getCustomers() {
        if(customers == null) {
            customers = new HashSet<>(customerService.getCustomers());
        }        
        return customers;
    }    
    
    public void editCustomer(Customer c) {
        clear();
        AddressDTO address;
        if(c instanceof PrivateCustomer) {
            activeTab = 0;
            PrivateCustomer pc = (PrivateCustomer)c;
            privateCustomer.setDateOfBirth(pc.getDateOfBirth());
            privateCustomer.setFirstName(pc.getFirstName());
            privateCustomer.setGender(pc.getGender());
            privateCustomer.setId(pc.getId());
            privateCustomer.setLastName(pc.getLastName());
            address = privateCustomer.getAddress();
        } else {
            activeTab = 1;
            CompanyCustomer cc = (CompanyCustomer)c;
            companyCustomer.setDateOfCreation(cc.getDateOfCreation());
            companyCustomer.setId(cc.getId());
            companyCustomer.setName(cc.getName());
            address = companyCustomer.getAddress();
        }   
        address.setCity(c.getAddress().getCity());
        address.setCountry(c.getAddress().getCountry());
        address.setCounty(c.getAddress().getCounty());
        address.setHouseNr(c.getAddress().getHouseNr());
        address.setStreet(c.getAddress().getStreet());
        address.setZip(c.getAddress().getZip());
    }
    
    public String editAccounts(Customer c) {
        accountModel.setCustomer(c);        
        return "int_account";
    }    
    
    public void save() {        
        Customer newOne = null;
        if(activeTab == 0) {
            newOne = customerService.updatePrivateCustomer(privateCustomer);
        } else if(companyCustomer != null) {
            newOne = customerService.updateCompanyCustomer(companyCustomer);
        }               
        customers.add(newOne);
        clear();
    }    
    
    public void clear() {
        privateCustomer = new PrivateCustomerDTO();    
        companyCustomer = new CompanyCustomerDTO();
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

    public int getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(int activeTab) {
        this.activeTab = activeTab;
    }
    
    
}
