/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jsf.model;

import de.jreichl.jpa.entity.StandingOrder;
import de.jreichl.jpa.entity.type.IntervalUnit;
import de.jreichl.service.BaseService;
import de.jreichl.service.interfaces.IStandingOrderService;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
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
public class StandingOrderModel extends BaseService implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static final DecimalFormat df = new DecimalFormat("###,##0.00");
    
    private String iban;
    private String amount = "0,00";
    private Date startDate;
    private int interval = 1;
    private IntervalUnit unit = IntervalUnit.MONTHLY;
    private String description;    
    
    private String message;
    
    @Inject
    private IStandingOrderService standingOrderService;

    @Inject
    private UserModel userModel;
    
    public void create() {
        try {
            StandingOrder order = standingOrderService.createStandingOrder(userModel.getCurrentAccount().getIban(),
                    iban, getAmountInCent(), startDate, interval, unit, description);
            userModel.refresh();
        } catch (ParseException ex) {
            logger.log(Level.SEVERE, "Failed to create standing order!", ex);
        }
        iban = null;
        amount = "0,00";
        startDate = null;
        interval = 1;
        unit = IntervalUnit.MONTHLY;
        description = null;
    }
    
    public void delete(StandingOrder order) {
        boolean deleted = standingOrderService.deleteStandingOrder(order);
        if(!deleted)
            logger.log(Level.WARNING, "Failed to delete standing order!");
    }
    
    private long getAmountInCent() throws ParseException {
        long amountInCent = (long)(df.parse(amount).doubleValue()*100);
        return amountInCent;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public IntervalUnit getUnit() {
        return unit;
    }

    public void setUnit(IntervalUnit unit) {
        this.unit = unit;
    }
    
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
