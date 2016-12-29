/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jsf;

import de.jreichl.jpa.entity.type.Gender;
import de.jreichl.jpa.entity.type.IntervalUnit;
import de.jreichl.jpa.entity.type.TanType;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author Jürgen Reichl
 */
@Named
@RequestScoped
public class EnumValuesProvider {
    
    public Gender[] getGenderValues() {
        return Gender.values();
    }
    
    public TanType[] getTanTypeValues() {
        return TanType.values();
    }    
    
    public IntervalUnit[] getIntervalUnitValues() {
        return IntervalUnit.values();
    }    
}
