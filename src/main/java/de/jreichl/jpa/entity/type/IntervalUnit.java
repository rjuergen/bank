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
    HOURLY(Calendar.HOUR, 1, "stündlich"),
    DAILY(Calendar.DATE, 1, "täglich"),
    WEEKLY(Calendar.DATE, 7, "wöchentlich"),
    MONTHLY(Calendar.MONTH, 1, "monatlich"),
    YEARLY(Calendar.YEAR, 1, "jährlich");
    
    private final int calendarType;
    private final int calendarAmount;
    private final String name;
    
    private IntervalUnit(int calendarType, int calendarAmount, String name) {
        this.calendarType = calendarType;
        this.calendarAmount = calendarAmount;
        this.name = name;
    }

    public String getName() {
        return name;
    }    
    
    public int getCalendarType() {
        return calendarType;
    }

    public int getCalendarAmount() {
        return calendarAmount;
    }       
    
}
