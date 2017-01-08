/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jsf.converter;

import de.jreichl.jpa.entity.Employee;
import de.jreichl.jpa.repository.EmployeeRepository;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author Jürgen Reichl
 */
@FacesConverter("de.jreichl.jsf.converter.EmployeeConverter")
public class EmployeeConverter implements Converter {

    @Inject
    private EmployeeRepository employeeRepo;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value==null)
            return "";
        Employee e = employeeRepo.findById(Long.valueOf(value));
        if(e==null)
            return "";
        return e;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value==null)
            return null;
        if(!value.getClass().equals(Employee.class))
            return null;
        return ((Employee)value).getId().toString();
    }
    
}
