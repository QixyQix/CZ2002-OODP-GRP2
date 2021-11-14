package entities;

/***
 * Represents a Staff entity
 * 
 * @author Lim Yan Kai
 * @version 1.0
 * @since 2021-11-14
 */
public class Staff extends Person implements Entities {
    /**
     * The id of this Staff
     */
    private int id;
    /**
     * The job Title of this Staff
     */
    private String jobTitle;

    /**
     * Constructor
     */
    public Staff() {
        super();
    }

    /**
     * Constructor
     * @param id        id of the Staff
     * @param jobTitle  Job Title of the Staff
     * @param name      Name of the Staff
     * @param contact   Contact of the Staff
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
