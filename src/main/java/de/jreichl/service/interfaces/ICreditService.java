/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.interfaces;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.Credit;
import java.util.Date;

/**
 *
 * @author Jürgen Reichl
 */
public interface ICreditService {
    
    public Credit takeCredit(Account account, long amountInCent, Date creationDate);
    
    public Credit payback(Account fromAccount, Credit credit, long amountInCent);
    
    public void updateInterestsToPay(Credit credit);
    
}
