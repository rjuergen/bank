/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.interfaces;

import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.service.exceptions.TransactionFailedException;
import java.sql.Timestamp;

/**
 *
 * @author Jürgen Reichl
 */
public interface ITransactionService {    
    
    public boolean transfer(long amountInCent, String fromIBAN, String toIBAN, String description) throws TransactionFailedException;
 
    public boolean transferStandingOrder(StandingOrder order, Timestamp newLastTransactionDate) throws TransactionFailedException;
    
}
