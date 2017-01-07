/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.common;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Jürgen Reichl
 */
public class DateTimeUtil {
    
    public static boolean isToday(Date date) {
        return isSameDate(date, new Date());
    }
    
    public static boolean isSameDate(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        return isSameDate(c1, c2);
    }
    
    public static boolean isSameDate(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && 
            c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
            c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH));
    }
    
}
