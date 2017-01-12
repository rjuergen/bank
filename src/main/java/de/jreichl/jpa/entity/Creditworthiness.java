/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import de.jreichl.common.AmountUtil;
import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

/**
 *
 * @author Jürgen Reichl
 */
@Entity
@NamedQuery(name="Creditworthiness.Customer",query="SELECT c FROM Creditworthiness c WHERE c.customer = :customer")
public class Creditworthiness extends SingleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
       
    private Date creationDate;
    
    @ManyToOne
    private Customer customer;
    
    /**
     * possible credit in cent
     */
    private long possibleCredit;
    
    
    public Creditworthiness() {
        
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getPossibleCredit() {
        return possibleCredit;
    }

    public void setPossibleCredit(long possibleCredit) {
        this.possibleCredit = possibleCredit;
    }
            
    public String getPossibleCreditFormatted() {
        return AmountUtil.getFormattedAmount(possibleCredit) + " €";
    }
    
}
