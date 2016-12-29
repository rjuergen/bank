/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import de.jreichl.jpa.entity.type.TransactionType;
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
public class AccountTransaction extends SingleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static final DecimalFormat df = new DecimalFormat("###,##0.00");
           
    @ManyToOne
    private Account account;
        
    /**
     * Type of the transaction: CREDIT(+) or DEBIT(-)
     */
    private TransactionType type;
    
    private Timestamp transactionDate;
    
    /**
     * value in cent
     */
    private long amount;
    
    private String description;

    public AccountTransaction(Account account, TransactionType type, long amount, Timestamp transactionDate) {
        this.account = account;
        this.type = type;
        this.amount = amount;  
        this.transactionDate = transactionDate;
    }
    
    protected AccountTransaction() {
        
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }    
    
    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    } 

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    public TransactionType getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }
    
    public String getAmountFormatted() {
        return df.format((double)amount / 100) + " €";
    }
    
}
