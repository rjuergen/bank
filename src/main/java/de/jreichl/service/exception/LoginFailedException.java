/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.exception;

/**
 *
 * @author Jürgen Reichl
 */
public class LoginFailedException extends Exception {

    private String accountNumber;
    
    public LoginFailedException(String accountNumber, String msg) {
        super(msg);
        this.accountNumber = accountNumber;
    }

    public LoginFailedException(String accountNumber, String msg, Exception ex) {
        super(msg, ex);
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }    
    
}
