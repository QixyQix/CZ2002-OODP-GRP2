package entities;

import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private String gender;
    private String contact;

    public Person(){}
    
    public Person(String name, String gender, String contact) {
        this.name = name;
        this.gender = gender;
        this.contact = contact;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return this.contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

}
