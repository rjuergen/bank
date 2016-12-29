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
    DEBIT("-", "red"),
    /**
     * Recorded as positive (+) in the balance of payments, any transaction 
     * that gives rise to a payment into the country, such as an export,
     * the sale of an asset (including official reserves), or borrowing 
     * from abroad. 
     * It is opposite of debit. 
     */
    CREDIT("+", "black");
    
    private String sign;
    private String color;
    
    private TransactionType(String sign, String color) {
        this.sign = sign;
        this.color = color;
    }

    public String getSign() {
        return sign;
    }

    public String getColor() {
        return color;
    }
    
    
}
