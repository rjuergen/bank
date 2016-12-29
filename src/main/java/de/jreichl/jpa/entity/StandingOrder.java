/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import de.jreichl.jpa.entity.type.IntervalUnit;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jürgen Reichl
 */
@Entity
public class StandingOrder extends SingleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static final DecimalFormat df = new DecimalFormat("###,##0.00");

    @ManyToOne
    private Account fromAccount; 
    
    @ManyToOne
    private Account toAccount; 
    
    /**
     * value in cent
     */
    private long amount;
    
    /**
     * the intervalAmount (in interval unit) for e.g. if you choose IntervalUnit.Hourly and have an intervalAmount=3 the standing order will run every 3 hours.
     */
    private int intervalAmount = 1;
    
    private IntervalUnit intervalUnit;
     
    private Timestamp startDate;
    
    private Timestamp lastTransaction;
    
    private String description;
    
    public StandingOrder() {
        
    }

    public StandingOrder(Account toAccount, IntervalUnit intervalUnit, int intervalAmount, long amountInCent, String description, Timestamp startDate) {
        this.toAccount = toAccount;
        this.intervalUnit = intervalUnit;
        this.intervalAmount = intervalAmount;
        this.amount = amountInCent;
        this.description = description;
        this.startDate = startDate;
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

    public int getInterval() {
        return intervalAmount;
    }

    public void setInterval(int interval) {
        this.intervalAmount = interval;
    }

    public IntervalUnit getIntervalUnit() {
        return intervalUnit;
    }

    public void setIntervalUnit(IntervalUnit intervalUnit) {
        this.intervalUnit = intervalUnit;
    }        
    
    public String getAmountFormatted() {
        return df.format((double)amount / 100) + " €";
    }
}
