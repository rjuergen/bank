/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity.type;

/**
 *
 * @author Jürgen Reichl
 */
public enum TanType {
    SMS_TAN("SMS TAN"),
    TAN_GENERATOR("TAN Generator");
    
    String name;
    
    private TanType(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
}
