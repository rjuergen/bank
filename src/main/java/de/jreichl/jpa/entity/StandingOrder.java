/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import de.jreichl.jpa.entity.type.StandingOrderType;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jürgen Reichl
 */
@Entity
public class StandingOrder extends SingleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Account fromAccount; 
    
    @ManyToOne
    private Account toAccount; 
    
    /**
     * value in cent
     */
    private long amount;
    
    private StandingOrderType type;
     
    private Timestamp startDate;
    
    private Timestamp lastTransaction;
    
    private String description;
    
    public StandingOrder() {
        
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }    

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getLastTransaction() {
        return lastTransaction;
    }

    public void setLastTransaction(Timestamp lastTransaction) {
        this.lastTransaction = lastTransaction;
    }
    

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public StandingOrderType getType() {
        return type;
    }

    public void setType(StandingOrderType type) {
        this.type = type;
    }
    
    
    
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
