package entities;

/***
 * Represents a report entity
 * 
 * @author Zong Yu Lee
 */
public class Staff extends Person implements Entities {
    private int id;
    private String jobTitle;

    public Staff() {
        super();
    }

    /**
     * Constructor
     */
    public Staff(int id, String jobTitle, String name, String gender, String contact) {
        super(name, gender, contact);
        this.id = id;
        this.jobTitle = jobTitle;
    }

    /**
     * Returns id
     * 
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns job title
     * 
     * @return job title
     */
    public String getJobTitle() {
        return this.jobTitle;
    }

    /**
     * Sets job title
     * 
     * @param jobTitle job title
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

}
