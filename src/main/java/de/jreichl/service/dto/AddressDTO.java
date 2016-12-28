/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.dto;

import java.io.Serializable;

/**
 *
 * @author Jürgen Reichl
 */
public class AddressDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    private String street;
    
    private String houseNr;
    
    private String city;
    
    private String zip;
    
    private String county;
    
    private String country;

    public AddressDTO(String street, String houseNr, String zip, String city, String county, String country) {
        this(street, houseNr, zip, city);
        this.county = county;
        this.country = country;
    }
    
    public AddressDTO(String street, String houseNr, String zip, String city) {
        this.street = street;
        this.houseNr = houseNr;
        this.zip = zip;
        this.city = city;
    }
    
    public AddressDTO() {
        
    }
    
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNr() {
        return houseNr;
    }

    public void setHouseNr(String houseNr) {
        this.houseNr = houseNr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    
    
}
