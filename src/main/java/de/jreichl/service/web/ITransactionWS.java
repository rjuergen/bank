/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.web;

import de.jreichl.service.exception.TransactionFailedException;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Jürgen Reichl
 */
@WebService
public interface ITransactionWS {
    
    @WebMethod
    public boolean transfer(@WebParam(name="amountInCent") long amountInCent, @WebParam(name="fromIBAN") String fromIBAN, @WebParam(name="toIBAN") String toIBAN,@WebParam(name="description") String description) throws TransactionFailedException;
    
}
