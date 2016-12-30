/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Jürgen Reichl
 */
@Entity
@NamedQuery(name="Credit.unpaid",query="SELECT c FROM Credit c WHERE c.paybackComplete = false")
public class Credit extends SingleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static final DecimalFormat df = new DecimalFormat("###,##0.00");
        
    private Timestamp creationDate;
    
    @ManyToOne
    private Customer customer;
    
    @ManyToOne
    private Account account;
        
    /**
     * credit in cent
     */
    private long credit;    
    
    @OneToMany
    private List<AccountTransaction> transactions = new ArrayList<>();
    
    /**
     * interest rate (Zinssatz) in ‱ (per ten thousand | 123‱ = 1.23% = 0.0123)
     */
    private int interestRate;
    
    /**
     * interest to pay back in cent
     */
    private long interestToPay = 0;
    
    /**
     * date of last addition of interest to toPay(remaining amount to pay back)
     */
    private Date interestAddedDate;
    
    private boolean paybackComplete = false;
    
    /**
     * standing order for payback
     */
    @OneToOne    
    private StandingOrder standingOrder;
    
    public Credit() {
        
    }    

    public StandingOrder getStandingOrder() {
        return standingOrder;
    }

    public void setStandingOrder(StandingOrder standingOrder) {
        this.standingOrder = standingOrder;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isPaybackComplete() {
        return paybackComplete;
    }

    public void setPaybackComplete(boolean paybackComplete) {
        this.paybackComplete = paybackComplete;
    }    
    
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public long getCredit() {
        return credit;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public String getCreditFormatted() {
        return df.format((double)credit / 100) + " €";
    }
    
    public int getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(int interestRate) {
        this.interestRate = interestRate;
    }

    public List<AccountTransaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public void setTransactions(List<AccountTransaction> transactions) {
        this.transactions = transactions;
    }

    public long getInterestToPay() {
        return interestToPay;
    }

    public void setInterestToPay(long interestToPay) {
        this.interestToPay = interestToPay;
    }       

    /**
     * date of last addition of interest to toPay(remaining amount to pay back)
     * @return interest added date
     */
    public Date getInterestAddedDate() {
        return interestAddedDate;
    }

    public void setInterestAddedDate(Date interestAddedDate) {
        this.interestAddedDate = interestAddedDate;
    }

    public void addTransaction(AccountTransaction t) {
        transactions.add(t);
    }

    
}
