package entities;

public class Person {
    private String name;
    private String gender;
    private int contact;

    public Person(String name, String gender, int contact) {
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

    public int getContact() {
        return this.contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

}
