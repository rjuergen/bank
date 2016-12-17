/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Jürgen Reichl
 */
@MappedSuperclass
public abstract class SingleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * ID in Long
     * @return the ID
     */
    public Long getId() {
        return id;
    }
    
    protected void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!getClass().isInstance(object)) {
            return false;
        }
        SingleEntity other = (SingleEntity) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }  
    
    @Override
    public String toString() {
        return String.format("%s[ id=%s ]",getClass().getName(), getId());
    }
    
}
