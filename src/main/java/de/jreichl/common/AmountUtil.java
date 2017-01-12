/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.common;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 *
 * @author Jürgen Reichl
 */
public class AmountUtil {
    
    private static final DecimalFormat df = new DecimalFormat("###,##0.00");
    
    public static String getFormattedAmount(long amountInCent) {
        return df.format((double)amountInCent / 100);
    }
    
    public static long parseFormattedAmount(String formattedAmount) throws ParseException {
        return (long)(df.parse(formattedAmount).doubleValue()*100);
    }
    
}
