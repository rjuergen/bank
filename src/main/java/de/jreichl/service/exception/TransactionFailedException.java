/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.exception;

import java.util.Date;


/**
 * Exception for failed Transactions
 * @author Jürgen Reichl
 */
public class TransactionFailedException extends Exception {
    private final String fromIBAN;
    private final String toIBAN;
    private final Date transactionDate;
    private final long amountInCent;

    public TransactionFailedException(Exception ex, String message, String fromIBAN, String toIBAN, Date transactionDate, long amountInCent) {
        super(message, ex);
        this.fromIBAN = fromIBAN;
        this.toIBAN = toIBAN;
        this.transactionDate = transactionDate;        
        this.amountInCent = amountInCent;
    }

    public String getFromIBAN() {
        return fromIBAN;
    }

    public String getToIBAN() {
        return toIBAN;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public long getAmountInCent() {
        return amountInCent;
    }



}