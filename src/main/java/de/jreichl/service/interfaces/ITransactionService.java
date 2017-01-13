/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.interfaces;

import de.jreichl.jpa.entity.Credit;
import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.service.exception.TransactionFailedException;
import java.sql.Timestamp;

/**
 *
 * @author Jürgen Reichl
 */
public interface ITransactionService {    
    
    public boolean transfer(long amountInCent, String fromIBAN, String toIBAN, String description) throws TransactionFailedException;
 
    public boolean transferStandingOrder(StandingOrder order, Timestamp newLastTransactionDate) throws TransactionFailedException;
    
    public boolean transferCashCredit(long amountInCent, String toIBAN, String description) throws TransactionFailedException;
    
    public boolean transferCashDebit(long amountInCent, String fromIBAN, String description) throws TransactionFailedException;
    
    public boolean transferCredit(Credit credit) throws TransactionFailedException;
    
    public boolean transferPayback(Credit credit, long amountInCent) throws TransactionFailedException;
    
    public void checkInput(long amountInCent, String fromIBAN, String toIBAN) throws TransactionFailedException;
}
