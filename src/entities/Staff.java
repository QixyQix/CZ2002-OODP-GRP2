package entities;


public class Staff extends Person {
    private int id;
    private String jobTitle;
    
    public Staff(int id, String jobTitle, String name, String gender, String contact) {
        super(name, gender, contact);
        this.id = id;
        this.jobTitle = jobTitle;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

}
