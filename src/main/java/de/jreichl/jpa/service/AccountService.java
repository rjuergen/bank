/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jürgen Reichl
 */
public class AccountService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
}
