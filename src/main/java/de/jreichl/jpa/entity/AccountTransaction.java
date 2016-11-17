/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import de.jreichl.jpa.entity.type.TransactionType;
import java.io.Serializable;
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
public class AccountTransaction extends SingleEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
        
    @ManyToOne
    private Account account;

        
    /**
     * Type of the transaction: CREDIT(+) or DEBIT(-)
     */
    private TransactionType type;
    
    /**
     * value in cent
     */
    private long amount;

    protected AccountTransaction() {
        
    }
      
    
    public AccountTransaction(TransactionType type, long amount) {
        this.type = type;
        this.amount = amount;
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
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }   

    @Override
    public String toString() {
        return "de.jreichl.jpa.entity.AccountTransaction[ id=" + id + " ]";
    }
    
}
