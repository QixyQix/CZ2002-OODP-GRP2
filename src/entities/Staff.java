package entities;


public class Staff extends Person {
    private String id;
    private String jobTitle;
    
    public Staff(String id, String jobTitle, String name, String gender, int contact) {
        super(name, gender, contact);
        this.id = id;
        this.jobTitle = jobTitle;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobTitle() {
        return this.jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

}
