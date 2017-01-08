/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service.biz;

import de.jreichl.jpa.entity.Account;
import de.jreichl.jpa.entity.Credit;
import de.jreichl.jpa.entity.Customer;
import net.poschinger.retailerposchinger.service.contractor.webservice.CreditWorthiness;

/**
 *
 * @author Jürgen Reichl
 */
public class CreditworthinessCalulator {
    
    public static long calculate(CreditWorthiness poschingerCreditWorthiness, Customer customer) {
        long possibleCredit = 0;        
        long accountBalanceWithoutCredits = getAccountBalanceWithoutCredits(customer);
        long ownPossibleCredit = getOwnPossibleCredit(accountBalanceWithoutCredits);
        long poschingerPossibleCredit = getPoschingerPossibleCredit(poschingerCreditWorthiness);
        
        double poschingerPercent = 0.0;
        if(poschingerPossibleCredit != -1)
            poschingerPercent = 0.5;
        double ownPercent = 1.0 - poschingerPercent;
        
        // credit calculation
        possibleCredit = (long)(poschingerPercent * poschingerPossibleCredit + ownPercent * ownPossibleCredit);
        
        return possibleCredit;
    } 
    
    private static long getPoschingerPossibleCredit(CreditWorthiness poschingerCreditWorthiness) {
        switch(poschingerCreditWorthiness) {
            case A:
                return 100000000;  // 1.000.000,00 €              
            case B:
                return 1000000; // 10.000,00 €
            case C:
                return 100000; // 1.000,00 €
            case D:
                return 10000; // 100,00 €
            case NODATA:
                return -1; // no data found
            default:
                return -1; // no data found
        }
    }
    
    private static long getOwnPossibleCredit(long accountBalanceWithoutCredits) {
        return (long)(accountBalanceWithoutCredits * 0.5);
    }
    
    private static long getAccountBalanceWithoutCredits(Customer customer) {
        long accountBalanceWithoutCredits = 0;
        for (Account a : customer.getAccounts()) {
            accountBalanceWithoutCredits += a.getBalance();
        }
        for (Credit c : customer.getCredits()) {
            if(!c.isPaybackComplete()) {
                accountBalanceWithoutCredits -= c.getRemainingPayback();
            }
        }
        return accountBalanceWithoutCredits;
    }
    
}
