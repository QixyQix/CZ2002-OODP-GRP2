package entities;

import java.io.Serializable;

/***
 * Represents a person entity
 * 
 * @author Lim Yan Kai
 * @version 1.0
 * @since 2021-11-14
 */
public class Person implements Serializable{
    /**
     * The name of this Person
     */
    private String name;
    /**
     * The gender of this Person
     */
    private String gender;
    /**
     * The contact of this Person
     */
    private String contact;

    /**
     * Constructor
     */
    public Person(){}
    
    /**
     * Constructor
     * @param name      Name of the person
     * @param gender    Gender of the person
     * @param contact   Contact of the person
     */
    public Person(String name, String gender, String contact) {
        this.name = name;
        this.gender = gender;
        this.contact = contact;
    }

    /**
     * Returns name
     * 
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets name
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns gender
     * 
     * @return gender
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Sets gender
     * 
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns contact
     * 
     * @return contact
     */
    public String getContact() {
        return this.contact;
    }

    /**
     * Sets contact
     * 
     * @return contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

}
