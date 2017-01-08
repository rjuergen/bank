/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.web;

import de.jreichl.service.exception.TransactionFailedException;
import de.jreichl.service.interfaces.ITransactionService;
import java.util.Date;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Jürgen Reichl
 */
@WebService
public class TransactionWS implements ITransactionWS {

    @Inject
    private ITransactionService transactionService;
    
    @Override
    @WebMethod
    public boolean transfer(@WebParam(name="amountInCent") long amountInCent, @WebParam(name="fromIBAN") String fromIBAN, @WebParam(name="toIBAN") String toIBAN,@WebParam(name="description") String description) throws TransactionFailedException {
        boolean result = false;
        try{
            result = transactionService.transfer(amountInCent, fromIBAN, toIBAN, description);
        } catch (TransactionFailedException ex) { 
            throw ex;
        } catch (Exception ex) {
            throw new TransactionFailedException(ex, "Unexpected Error! Transaction failed. Check if it's the right IBAN and there is enough money on your account.", fromIBAN, toIBAN, new Date(), amountInCent);
        }
        return result;
    }
    
}
