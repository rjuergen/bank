/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.CompanyCustomer;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.entity.PrivateCustomer;
import de.jreichl.jpa.entity.embeddable.Address;
import de.jreichl.jpa.entity.type.Gender;
import de.jreichl.jpa.repository.CompanyCustomerRepository;
import de.jreichl.jpa.repository.PrivateCustomerRepository;
import de.jreichl.service.dto.AddressDTO;
import de.jreichl.service.interfaces.ICustomerService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * Service to create private and company customers
 * @author Jürgen Reichl
 */
@RequestScoped
public class CustomerService extends BaseService implements ICustomerService {
    
    @Inject
    private PrivateCustomerRepository privateCustomerRepo;
 
    @Inject
    private CompanyCustomerRepository companyCustomerRepo;

    
    @Transactional
    @Override
    public PrivateCustomer createPrivateCustomer(String firstName, String lastName, Gender gender, Date dateOfBirth, AddressDTO address) {
        PrivateCustomer c = new PrivateCustomer();
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setGender(gender);        
        c.setDateOfBirth(new java.sql.Date(dateOfBirth.getTime()));
        
        Address a = createAddress(address);
        c.setAddress(a);
        
        privateCustomerRepo.persist(c);
        
        return c;
    }

    
    @Transactional
    @Override
    public CompanyCustomer createCompanyCustomer(String name, Date dateOfCreation, AddressDTO address) {
        CompanyCustomer c = new CompanyCustomer();
        c.setName(name);    
        c.setDateOfCreation(new java.sql.Date(dateOfCreation.getTime()));
        
        Address a = createAddress(address);
        c.setAddress(a);
        
        companyCustomerRepo.persist(c);
        
        return c;
    }
    
    @Transactional
    private Address createAddress(AddressDTO dto) {
        Address a = new Address();
        a.setHouseNr(dto.getHouseNr());
        a.setStreet(dto.getStreet());
        a.setZip(dto.getZip());
        a.setCity(dto.getCity());
        a.setCounty(dto.getCounty());
        a.setCountry(dto.getCountry()); 
        return a;
    }

    public List<Customer> getCustomers() {
        List<Customer> customer = new ArrayList<>();
        customer.addAll(privateCustomerRepo.findAll());
        customer.addAll(companyCustomerRepo.findAll());        
        return customer;
    }
    
}
