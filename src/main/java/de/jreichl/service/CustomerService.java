/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.CompanyCustomer;
import de.jreichl.jpa.entity.Customer;
import de.jreichl.jpa.entity.PrivateCustomer;
import de.jreichl.jpa.entity.embeddable.Address;
import de.jreichl.jpa.repository.CompanyCustomerRepository;
import de.jreichl.jpa.repository.PrivateCustomerRepository;
import de.jreichl.service.dto.AddressDTO;
import de.jreichl.service.dto.CompanyCustomerDTO;
import de.jreichl.service.dto.PrivateCustomerDTO;
import de.jreichl.service.interfaces.ICustomerService;
import java.util.ArrayList;
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
    public PrivateCustomer updatePrivateCustomer(PrivateCustomerDTO dto) {
        PrivateCustomer c;
        if(dto.isNew())
            c = new PrivateCustomer();
        else
            c = privateCustomerRepo.findById(dto.getId());
        
        c.setFirstName(dto.getFirstName());
        c.setLastName(dto.getLastName());
        c.setGender(dto.getGender());        
        c.setDateOfBirth(new java.sql.Date(dto.getDateOfBirth().getTime()));
        
        Address a = createAddress(dto.getAddress());
        c.setAddress(a);
        
        privateCustomerRepo.persist(c);
        
        return c;
    }

    @Transactional
    @Override
    public CompanyCustomer updateCompanyCustomer(CompanyCustomerDTO dto) {
        CompanyCustomer c;
        if(dto.isNew())
            c = new CompanyCustomer();
        else
            c = companyCustomerRepo.findById(dto.getId());
        
        c.setName(dto.getName());    
        c.setDateOfCreation(new java.sql.Date(dto.getDateOfCreation().getTime()));
        
        Address a = createAddress(dto.getAddress());
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

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customer = new ArrayList<>();
        customer.addAll(privateCustomerRepo.findAll());
        customer.addAll(companyCustomerRepo.findAll());        
        return customer;
    }

    @Transactional
    @Override
    public void persistCustomer(Customer c) {
        if(c instanceof PrivateCustomer)
            privateCustomerRepo.persist((PrivateCustomer)c);
        else if (c instanceof CompanyCustomer)
            companyCustomerRepo.persist((CompanyCustomer)c);
    }

    @Override
    public Customer findCustomer(Customer c) {
        if(c instanceof PrivateCustomer)
            return privateCustomerRepo.findById(c.getId());
        else
            return companyCustomerRepo.findById(c.getId());
    }
    
}
