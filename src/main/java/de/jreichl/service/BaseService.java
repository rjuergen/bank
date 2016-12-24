/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.service;

import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author Jürgen Reichl
 */
public abstract class BaseService {
    
    @Inject
    protected transient Logger logger; 
    
}
