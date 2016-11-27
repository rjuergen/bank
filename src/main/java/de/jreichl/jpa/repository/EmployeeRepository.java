/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.Employee;

/**
 *
 * @author Jürgen Reichl
 */
public class EmployeeRepository extends SingleEntityRepository<Employee>{
    
    public EmployeeRepository() {
        super(Employee.class);
    }
    
}
