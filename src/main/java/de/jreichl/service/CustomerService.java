/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.CompanyCustomer;
import de.jreichl.jpa.entity.PrivateCustomer;
import de.jreichl.jpa.entity.embeddable.Address;
import de.jreichl.jpa.entity.type.Gender;
import de.jreichl.jpa.repository.CompanyCustomerRepository;
import de.jreichl.jpa.repository.PrivateCustomerRepository;
import java.util.Date;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 *
 * @author Jürgen Reichl
 */
public class CustomerService {
    
    @Inject
    private PrivateCustomerRepository privateCustomerRepo;
 
    @Inject
    private CompanyCustomerRepository companyCustomerRepo;
    
    @Transactional
    public PrivateCustomer createPrivateCustomer(String firstName, String lastName, Gender gender, Date dateOfBirth, String houseNr, String street, String zip, String city, String county, String country) {
        PrivateCustomer c = new PrivateCustomer();
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setGender(gender);        
        c.setDateOfBirth(new java.sql.Date(dateOfBirth.getTime()));
        
        Address a = new Address();
        a.setHouseNr(houseNr);
        a.setStreet(street);
        a.setZip(zip);
        a.setCity(city);
        a.setCounty(county);
        a.setCountry(country); 
        c.setAddress(a);
        
        privateCustomerRepo.persist(c);
        
        return c;
    }
    
        @Transactional
    public CompanyCustomer createCompanyCustomer(String name, Date dateOfCreation, String houseNr, String street, String zip, String city, String county, String country) {
        CompanyCustomer c = new CompanyCustomer();
        c.setName(name);    
        c.setDateOfCreation(new java.sql.Date(dateOfCreation.getTime()));
        
        Address a = new Address();
        a.setHouseNr(houseNr);
        a.setStreet(street);
        a.setZip(zip);
        a.setCity(city);
        a.setCounty(county);
        a.setCountry(country); 
        c.setAddress(a);
        
        companyCustomerRepo.persist(c);
        
        return c;
    }
    
    @Transactional
    public PrivateCustomer createDummyPrivateCustomer() {
        // PrivateCustomer (entity) anlegen (im Heap)
        PrivateCustomer c = new PrivateCustomer();
        c.setFirstName("Hans");
        c.setLastName("Meier");
        c.setGender(Gender.MALE);        
        
        privateCustomerRepo.persist(c);
        
        return c;
    }
    
}
