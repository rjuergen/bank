/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jsf.model;

import de.jreichl.jpa.entity.Bank;
import de.jreichl.service.BaseService;
import de.jreichl.service.interfaces.IBankService;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Jürgen Reichl
 */
@Named
@SessionScoped
public class BankModel extends BaseService implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Bank bank;

    @Inject
    private IBankService bankService;
    
    public Bank getBank() {
        if(bank == null)
            bank = bankService.getBank();
        return bank;
    }
    
}
