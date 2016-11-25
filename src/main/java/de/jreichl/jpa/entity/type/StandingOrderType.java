/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity.type;

/**
 *
 * @author Jürgen Reichl
 */
public enum StandingOrderType {
    HOURLY(1000*60*60),
    DAILY(1000*60*60*24),
    WEEKLY(1000*60*60*24*7),
    MONTHLY(0),
    YEARLY(0);
    
    private final long milliseconds;
    
    private StandingOrderType(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public long getMilliseconds() {
        return milliseconds;
    }    
    
}
