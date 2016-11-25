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
public enum StandingOrderType {
    HOURLY(Calendar.HOUR),
    DAILY(Calendar.DATE),
    WEEKLY(Calendar.DATE),
    MONTHLY(Calendar.MONTH),
    YEARLY(Calendar.YEAR);
    
    private final int calendarType;
    
    private StandingOrderType(int calendarType) {
        this.calendarType = calendarType;
    }

    public int getCalendarType() {
        return calendarType;
    }
    
}
