/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import de.jreichl.jpa.entity.Bank;
import de.jreichl.jpa.repository.BankRepository;
import de.jreichl.service.interfaces.IBankService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Jürgen Reichl
 */
@RequestScoped
public class BankService extends BaseService implements IBankService {

    @Inject
    private BankRepository bankRepo;
    
    @Override
    public Bank getBank() {
        return bankRepo.getBank();
    }
    
}
