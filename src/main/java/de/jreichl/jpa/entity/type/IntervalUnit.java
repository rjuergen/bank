/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity.type;

import java.util.Calendar;

/**
 *
 * @author Jürgen Reichl
 */
public enum IntervalUnit {
    HOURLY(Calendar.HOUR, 1),
    DAILY(Calendar.DATE, 1),
    WEEKLY(Calendar.DATE, 7),
    MONTHLY(Calendar.MONTH, 1),
    YEARLY(Calendar.YEAR, 1);
    
    private final int calendarType;
    private final int calendarAmount;
    
    private IntervalUnit(int calendarType, int calendarAmount) {
        this.calendarType = calendarType;
        this.calendarAmount = calendarAmount;
    }

    public int getCalendarType() {
        return calendarType;
    }

    public int getCalendarAmount() {
        return calendarAmount;
    }       
    
}
