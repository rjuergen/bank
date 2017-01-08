/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.interfaces;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.Credit;
import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.service.exception.TransactionFailedException;
import java.util.Date;

/**
 *
 * @author Jürgen Reichl
 */
public interface ICreditService {
    
    public Credit takeCredit(Account account, long amountInCent, int interestRateInPerTenThousand, Date creationDate) throws TransactionFailedException;
    
    public Credit payback(Credit credit, long amountInCent) throws TransactionFailedException;
    
    public StandingOrder updatePaybackStandingOrder(Account fromAccount, Credit credit, long monthlyAmountInCent);
    
    public void updateInterestsToPay(Credit credit);
    
}
