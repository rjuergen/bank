/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.Bank;
import java.util.List;

/**
 *
 * @author Jürgen Reichl
 */
public class BankRepository extends SingleEntityRepository<Bank>{
    
    public BankRepository() {
        super(Bank.class);
    }
    
    public Bank getBank() {
        List<Bank> r = findAll();        
        return r.get(0);
    }
    
}
