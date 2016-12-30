/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jsf.model;

import de.jreichl.jpa.entity.Credit;
import de.jreichl.jpa.entity.Creditworthiness;
import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.service.BaseService;
import de.jreichl.service.interfaces.ICreditService;
import de.jreichl.service.interfaces.ICreditworthinessService;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
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
public class CreditModel extends BaseService implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * interest rate (Zinssatz) in ‱ (per ten thousand | 123‱ = 1.23% = 0.0123)
     */
    private int interestRate = 215;
    
    private static final DecimalFormat df = new DecimalFormat("###,##0.00");
    private String message;    
    
    private List<Credit> credits;
    private Creditworthiness creditworthiness;
    
    private String creditAmount = "0,00";
    
    private Credit selectedCredit;
    private String paybackAmount = "0,00";
    private String monthlyPaybackAmount = "0,00";
    
    @Inject 
    private UserModel userModel;
    
    @Inject
    private ICreditService creditService;
    
    @Inject
    private ICreditworthinessService creditworthinessService;
    
    public List<Credit> getCredits() {
        return credits;
    }    
    
    public String getPaybackAmount(Credit c) {
        long payback = creditService.getRemainingPayback(c);        
        return df.format((double)payback / 100) + " €";
    }
    
    public void requestCreditworthiness() {
        if(creditworthiness == null)
            creditworthiness = creditworthinessService.requestCreditworthiness(userModel.getCurrentUser());
    }

    public String getMessage() {
        return message;
    }    
    
    public Creditworthiness getCreditworthiness() {
        return creditworthiness;
    }
    
    public String getInterestRate() {
        return df.format((double)interestRate / 100) + " %";
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getPaybackAmount() {
        return paybackAmount;
    }

    public void setPaybackAmount(String paybackAmount) {
        this.paybackAmount = paybackAmount;
    }
    
    public String getMonthlyPaybackAmount() {
        return monthlyPaybackAmount;
    }

    public void setMonthlyPaybackAmount(String monthlyPaybackAmount) {
        this.monthlyPaybackAmount = monthlyPaybackAmount;
    }
    
    private long getAmountInCent(String amount) throws ParseException {
        long amountInCent = (long)(df.parse(amount).doubleValue()*100);
        return amountInCent;
    }
    
    public void takeCredit() {
        try {
            Credit c = creditService.takeCredit(userModel.getCurrentAccount(), getAmountInCent(creditAmount), interestRate, new Date());
            credits.add(c);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to transfer credit to account", ex);
            message = "Kredit überweisen fehlgeschlagen!";
        }
    }
    
    public void paybackCredit() {
        try {
            Credit c = creditService.payback(selectedCredit, getAmountInCent(paybackAmount));
            credits.set(credits.indexOf(c), c);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Failed to transfer credit payback to account", ex);
            message = "Kreditrückzahlung fehlgeschlagen!";
        }
    }

    public void createStandingOrder() {
        try {
            StandingOrder order = creditService.updatePaybackStandingOrder(userModel.getCurrentAccount(), selectedCredit, getAmountInCent(monthlyPaybackAmount));
            if(order != null)
                message = "Monatlicher Dauerauftrag für Kreditszurückzahlung erfolgreich erstellt.";
        } catch (ParseException ex) {
            logger.log(Level.SEVERE, "Failed to cast monthly payback to long!", ex);
            message = "Falsches Format des monatlichen Betrags! Dauerauftrag für Kredit konnte nicht erstellt werden.";
        }
    }
    
    public Credit getSelectedCredit() {
        return selectedCredit;
    }
    
    public void select(Credit c) {
        selectedCredit = c;
        if(c.getStandingOrder() != null)
            monthlyPaybackAmount = df.format((double)c.getStandingOrder().getAmount() / 100);
        paybackAmount = "0,00";        
    }
    
}
