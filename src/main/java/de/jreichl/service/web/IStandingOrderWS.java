/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.web;

import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.jpa.entity.type.IntervalUnit;
import java.util.Date;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Jürgen Reichl
 */
@WebService
public interface IStandingOrderWS {
    
    @WebMethod
    public StandingOrder createStandingOrder( @WebParam(name="fromIBAN") String fromIBAN, @WebParam(name="toIBAN") String toIBAN, @WebParam(name="amountInCent") long amountInCent, @WebParam(name="startDate") Date startDate, @WebParam(name="interval") int interval, @WebParam(name="IntervalUnit") IntervalUnit unit, @WebParam(name="description") String description);
    
}
