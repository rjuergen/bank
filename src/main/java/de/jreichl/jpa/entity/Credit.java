/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Jürgen Reichl
 */
@Entity
@NamedQuery(name="Credit.unpaid",query="SELECT c FROM Credit c WHERE c.paybackComplete = false")
public class Credit extends SingleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
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
    private List<AccountTransaction> transactions;
    
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
    
    
    public Credit() {
        
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

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
     */
    public Date getInterestAddedDate() {
        return interestAddedDate;
    }

    public void setInterestAddedDate(Date interestAddedDate) {
        this.interestAddedDate = interestAddedDate;
    }

    
}
