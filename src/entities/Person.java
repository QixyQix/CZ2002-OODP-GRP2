package entities;

import java.io.Serializable;

/***
 * Represents a person entity
 * 
 * @author Zong Yu Lee
 */
public class Person implements Serializable{
    private String name;
    private String gender;
    private String contact;

    public Person(){}
    
    /**
     * Constructor
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
     * @param contact contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

}
