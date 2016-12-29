/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jsf.model;

import de.jreichl.service.BaseService;
import de.jreichl.service.exception.TransactionFailedException;
import de.jreichl.service.interfaces.ITransactionService;
import java.io.Serializable;
import java.util.logging.Level;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Jürgen Reichl
 */
@Named
@SessionScoped
public class TransactionModel extends BaseService implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private long cashAmountInEuro = 0;
    private String description;    
    
    private String message;
   
    @Inject
    private ITransactionService transactionService;
    
    @Inject
    private UserModel userModel;

    public String getMessage() {
        return message;
    }    
    
    public long getCashAmountInEuro() {
        return cashAmountInEuro;
    }

    public void setCashAmountInEuro(long cashAmountInEuro) {
        this.cashAmountInEuro = cashAmountInEuro;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public void creditCash() {
        boolean transfered = false;
        try {
            transfered = transactionService.transferCashCredit(cashAmountInEuro*100, userModel.getCurrentAccount().getIban(), description);
        } catch (TransactionFailedException ex) {
            logger.log(Level.SEVERE, "Failed to transfer cash (credit)!", ex);
        }
        if(transfered)
            message = "Geld erfolgreich eingezahlt!";
        else
            message = "Geld einzahlen fehlgeschlagen!";
        cashAmountInEuro = 0;
        description = null;
        userModel.refresh();
    }
    
    public void debitCash() {
        boolean transfered = false;
        try {
            transfered = transactionService.transferCashDebit(cashAmountInEuro*100, userModel.getCurrentAccount().getIban(), description);
        } catch (TransactionFailedException ex) {
            logger.log(Level.SEVERE, "Failed to transfer cash (credit)!", ex);
        }
        if(transfered)
            message = "Geld erfolgreich ausgezahlt!";
        else
            message = "Geld auszahlen fehlgeschlagen!";
        cashAmountInEuro = 0;
        description = null;
        userModel.refresh();
    }
}
