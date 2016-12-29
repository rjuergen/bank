/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jsf.model;

import de.jreichl.service.BaseService;
import de.jreichl.service.exception.TransactionFailedException;
import de.jreichl.service.interfaces.ITransactionService;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
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
    
    private static final DecimalFormat df = new DecimalFormat("###,##0.00");
    
    private String iban;
    private String amount = "0,00";
    private String description;    
    
    private String message;
   
    @Inject
    private ITransactionService transactionService;
    
    @Inject
    private UserModel userModel;

    public String getMessage() {
        return message;
    }    

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
    
    private long getAmountInCent() throws ParseException {
        long amountInCent = (long)(df.parse(amount).doubleValue()*100);
        return amountInCent;
    }
    
    public void transferCreditCash() {
        boolean transfered = false;
        try {
            transfered = transactionService.transferCashCredit(getAmountInCent(), userModel.getCurrentAccount().getIban(), description);
        } catch (TransactionFailedException | ParseException ex) {
            logger.log(Level.SEVERE, "Failed to transfer cash (credit)!", ex);
        }
        if(transfered)
            message = "Geld erfolgreich eingezahlt!";
        else
            message = "Geld einzahlen fehlgeschlagen!";
        amount = "0,00";
        description = null;
        userModel.refresh();
    }
    
    public void transferDebitCash() {
        boolean transfered = false;
        try {
            transfered = transactionService.transferCashDebit(getAmountInCent(), userModel.getCurrentAccount().getIban(), description);
        } catch (TransactionFailedException  | ParseException ex) {
            logger.log(Level.SEVERE, "Failed to transfer cash (credit)!", ex);
        }
        if(transfered)
            message = "Geld erfolgreich ausgezahlt!";
        else
            message = "Geld auszahlen fehlgeschlagen!";
        amount = "0,00";
        description = null;
        userModel.refresh();
    }
    
    public void transfer() {
        boolean transfered = false;
        try {
            transfered = transactionService.transfer(getAmountInCent(), userModel.getCurrentAccount().getIban(), iban, description);
        } catch (TransactionFailedException  | ParseException ex) {
            logger.log(Level.SEVERE, "Failed to transfer cash (credit)!", ex);
        }
        if(transfered)
            message = "Geld erfolgreich überwiesen!";
        else
            message = "Geld Überweisung fehlgeschlagen!";
        iban = "";
        amount = "0,00";
        description = null;
        userModel.refresh();
    }
    
}
