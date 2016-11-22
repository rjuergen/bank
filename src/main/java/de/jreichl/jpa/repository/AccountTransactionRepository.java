/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.AccountTransaction;

/**
 *
 * @author Jürgen Reichl
 */
public class AccountTransactionRepository extends SingleEntityRepository<AccountTransaction> {
    
    public AccountTransactionRepository() {
        super(AccountTransaction.class);
    }
    
}
