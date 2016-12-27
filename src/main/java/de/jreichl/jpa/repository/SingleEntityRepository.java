/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.repository;

import de.jreichl.jpa.entity.SingleEntity;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jürgen Reichl
 * @param <E>
 */
public abstract class SingleEntityRepository<E extends SingleEntity> {
    
    private Class<E> entityClass;
    
    @PersistenceContext
    protected EntityManager em;
 
    public void persist(E entity) {
        em.persist(entity);
    }
    
    public E merge(E entity) {
        return em.merge(entity);
    }
    
    public void remove(E entity) {
        em.remove(entity);
    }
    
    public E findById(long id) {
        return em.find(getEntityClass(), id);
    }
    
    public List<E> findAll() {
        TypedQuery<E> query = em.createQuery(String.format("Select e From %s As e",getEntityClass().getSimpleName()), getEntityClass());        
        return query.getResultList();
    }    
    
        // Klassenname ermitteln mit Hilfe Java-Reflection
    protected Class<E> getEntityClass()
    {
        // falls Klasse noch nicht gesucht wurde, dann jetzt
        if (this.entityClass == null)
        {
            // Eigene Klasse holen
            // Der zurückgegebene Klassenname ist der, der konkreten (!) Klasse, nicht dieser abstrakten Klasse
            Class<?> repoClass = getClass();
            // solange...
            while (true)
            {
                // ... repoClass noch eine Klasse hat, von der sie abgeleitet ist...
                Class<?> baseClass = repoClass.getSuperclass();
                // Kurzform für: if(baseClass!=null) { /* was nach dem : steht */ } else { throw new AssertionViolationException() }
                // ... und die auch bitte von SingleIdEntityRepository abgeleitet sein muss ...
                assert baseClass != null : "Ihr Repository muss von Klasse " + SingleEntityRepository.class.getName() + " abgeleitet sein!";

                // ... solange geht die Vererbungshierarchie runter...
                if (baseClass == SingleEntityRepository.class)
                {
                    // ...bis zu (Gross)Vater == SingleIdEntityRepository...
                    break;
                }

                repoClass = baseClass;
            }

            // repoClass ist jetzt eine "ueber" SingleEntityRepository<E extends SingleEntity>
            // mit getGenericSuperclass bekommt man Klassenobjekt vom Vater, der mit Generics parametriert ist
            Type genericSuperClass = repoClass.getGenericSuperclass();
            assert genericSuperClass instanceof ParameterizedType : SingleEntityRepository.class.getName() + " must be generic";

            // .. der Vater (SingleEntityRepository wurde mit einem konkreten Datentypen parametriert, z. B. <SingleEntity>
            Type[] typeParms = ((ParameterizedType) genericSuperClass).getActualTypeArguments();
            assert typeParms.length == 1 : SingleEntityRepository.class.getName() + " muss exakt 1 Parameter besitzen, hat aber " + typeParms.length + " Parameter";

            // davon nimm den 1. Parametertyp, das ist der Datentyp der Id (des Primärschlüssels)
            Type entityType = typeParms[0];

            if (entityType instanceof ParameterizedType)
            {
                entityType = ((ParameterizedType) entityType).getRawType();
            }

            assert entityType instanceof Class<?> : "Entity muss eine Klasse sein (kein Interface, Array, ...)";

            // den Datentyp braucht man, um die TypedQuery in findAll() mit dem richtigen Datentyp zu "bestücken"
            this.entityClass = (Class<E>) entityType;
        }

        // oder vom letzten mal Suchen nehmen
        return this.entityClass;
    }
    
}
