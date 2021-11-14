package managers;

import java.io.IOException;
import java.util.HashMap;

import entities.Entities;
import entities.Staff;
import exceptions.IDNotFoundException;

/***
 * Represents a staff manager
 * 
 * @author Lee Zong Yu
 * @version 1.0
 * @since 2021-11-14
 */
public final class StaffMgr extends DataMgr {
    /**
     * The Instance of this StaffMgr
     */
    private static StaffMgr INSTANCE;
    /**
     * The mapping of Staff ID to its respective object
     */
    private HashMap<Integer, Staff> staffs;
    /**
     * The next Id to be use in creating staff
     */
    private int nextId;

    /**
     * Constructor
     */
    private StaffMgr() {
        try {
            this.staffs = new HashMap<Integer, Staff>();
            downCast(super.loadSavedData("staffs"));
            this.nextId = super.loadNextIdData("staffNextId");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load staffs data");
        }
    }

    /**
     * Downcast from entities to Staff
     * 
     * @param object the entities to downcast
     */
    public void downCast(HashMap<Integer, Entities> object) {
        for (int id : object.keySet()) {
            if (object.get(id) instanceof Staff)
                this.staffs.put(id, (Staff) object.get(id));
            else
                throw new ClassCastException();
        }
    }

    /**
     * Upcast Staff to entities in a hashmap
     * 
     * @return Hashmap object
     */
    public HashMap<Integer, Entities> upCast() {
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for (int id : staffs.keySet()) {
            object.put(id, staffs.get(id));
        }
        return object;
    }

    /***
     * Save data
     * 
     * @throws IOException if stream to file cannot be written to or closed
     */
    public void saveData() throws IOException {
        saveDataSerialize(upCast(), nextId, "staffs", "staffNextId");
    }

    /**
     * Returns the StaffMgr instance and creates an instance if it does not exist
     * 
     * @return instance
     */
    public static StaffMgr getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StaffMgr();
        }

        return INSTANCE;
    }

    /**
     * Returns Staff object or null
     * 
     * @param staffId id of staff
     * @return Staff object if staff exists, null if staff does not exist
     */
    public Staff checkexisitingStaff(int staffId) throws IDNotFoundException {

        Staff staff = this.staffs.get(staffId);

        if (staff == null)
            throw new IDNotFoundException();
        return staff;

    }

    /**
     * Returns true or false depending if staff corresponding to the contact number
     * exists
     * 
     * @param phoneNumber contact number
     * @return true if staff exists, false if staff does not exist
     */
    /*
     * public boolean checkexisitingStaff(String phoneNumber) {
     * 
     * return true; }
     */
    /**
     * Creates and returns Staff object
     * 
     * @param jobTitle staff job title
     * @param name     staff name
     * @param gender   staff gender
     * @param contact  staff contact number
     * @return Staff object
     */
    public Staff createStaff(String jobTitle, String name, String gender, String contact) {
        Staff staff = new Staff(nextId, jobTitle, name, gender, contact);
        this.staffs.put(nextId, staff);
        this.nextId++;
        return staff;
    }

}
