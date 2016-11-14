/*
 * License: Free to use. It's just a small project.
 * Feel free and use everything you want  * 
 */
package de.jreichl.jpa.entity.type;

/**
 *
 * @author Jürgen Reichl
 */
public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private String name;

    private Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
