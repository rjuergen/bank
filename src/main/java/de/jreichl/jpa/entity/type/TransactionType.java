/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity.type;

/**
 * TransactionType defines if the Transaction is a DEBIT (-) or a CREDIT (+)
 * @author Jürgen Reichl
 */
public enum TransactionType {
    /**
     * Recorded as negative (-) in the balance of payments, any transaction
     * that gives rise to a payment out of the country, such as an import,
     * the purchase of an asset (including official reserves), or lending
     * to foreigners. 
     * Opposite of credit.
     */
    DEBIT,
    /**
     * Recorded as positive (+) in the balance of payments, any transaction 
     * that gives rise to a payment into the country, such as an export,
     * the sale of an asset (including official reserves), or borrowing 
     * from abroad. 
     * It is opposite of debit. 
     */
    CREDIT;
}
