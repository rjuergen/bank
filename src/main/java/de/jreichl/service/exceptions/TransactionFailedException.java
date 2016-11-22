/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.exceptions;

import de.jreichl.jpa.entity.type.TransactionType;
import java.util.Date;


/**
 * Exception for failed Transactions
 * @author Jürgen Reichl
 */
public class TransactionFailedException extends Exception {
    private final String IBAN;
    private final Date transactionDate;
    private final TransactionType type;
    private final long amountInCent;

    public TransactionFailedException(Exception ex, String message, String IBAN, Date transactionDate, TransactionType type, long amountInCent) {
        super(message, ex);
        this.IBAN = IBAN;
        this.transactionDate = transactionDate;
        this.type = type;
        this.amountInCent = amountInCent;
    }

    public String getIBAN() {
        return IBAN;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public TransactionType getType() {
        return type;
    }

    public long getAmountInCent() {
        return amountInCent;
    }

}