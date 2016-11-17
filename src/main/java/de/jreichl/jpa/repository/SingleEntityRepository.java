/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.SingleEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jürgen Reichl
 */
public abstract class SingleEntityRepository<E extends SingleEntity> {
    
    private final Class<E> clazz;
    
    @PersistenceContext
    private EntityManager em;
    
    protected SingleEntityRepository(Class<E> clazz) {
        this.clazz = clazz;
    }
 
    public void persist(E entity) {
        em.persist(entity);
    }
    
    public E merge(E entity) {
        return em.merge(entity);
    }
    
    public void remove(E entity) {
        em.remove(entity);
    }
    
    public E findById(int id) {
        return em.find(clazz, id);
    }
    
    public List<E> findAll() {
        TypedQuery<E> query = em.createQuery(String.format("Select e From %s As e",clazz.getSimpleName()), clazz);        
        return query.getResultList();
    }    
    
}
