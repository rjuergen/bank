/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.interfaces;

import de.jreichl.service.exceptions.TransactionFailedException;

/**
 *
 * @author Jürgen Reichl
 */
public interface ITransactionService {    
    
    public boolean transfer(long amountInCent, String fromIBAN, String toIBAN, String description) throws TransactionFailedException;
    
}
