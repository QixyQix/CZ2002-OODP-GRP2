package entities;

public class Staff extends Person implements Entities{
    private int id;
    private String jobTitle;
    
    public Staff(){
        super();
    }

    public Staff(int id, String jobTitle, String name, String gender, String contact) {
        super(name, gender, contact);
        this.id = id;
        this.jobTitle = jobTitle;
    }

    public int getId() {
        return this.id;
    }


    public String getJobTitle() {
        return this.jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

}
