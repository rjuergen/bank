/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.Customer;

/**
 *
 * @author Jürgen Reichl
 * @param <E>
 */
public abstract class CustomerRepository<E extends Customer> extends SingleEntityRepository<E> {
       
}
