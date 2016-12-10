/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.Credit;

/**
 *
 * @author Jürgen Reichl
 */
public class CreditRepository extends SingleEntityRepository<Credit> {
    
    public CreditRepository() {
        super(Credit.class);
    }
    
}
