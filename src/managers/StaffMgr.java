package managers;

import java.io.IOException;
import java.util.HashMap;

import entities.Entities;
import entities.Staff;
import exceptions.IDNotFoundException;

/***
 * Represents a staff manager
 * 
 * @author Zong Yu Lee
 */
public final class StaffMgr extends DataMgr{
    private static StaffMgr instance;
    private HashMap<Integer, Staff> staffs;
    private int nextId; // will be delete afterward

    private StaffMgr() {
        try {
            this.staffs = new HashMap<Integer, Staff>();
            downcast(super.loadSavedData("staffs"));
            this.nextId = super.loadNextIdData("staffNextId");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load staffs data");
        }
    }

    private void downcast(HashMap<Integer, Entities> object){
        for(int id: object.keySet()){
            if(object.get(id) instanceof Staff)
                this.staffs.put(id,(Staff) object.get(id));
            else throw new ClassCastException();
        }
    }

    private HashMap<Integer, Entities> upcast(){
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for(int id: staffs.keySet()){
           object.put(id,staffs.get(id)); 
        }
        return object;
    }
    
    public void saveData() throws IOException {
        saveDataSerialize(upcast(), nextId, "staffs", "staffNextId");
    }

    /**
     * Returns the StaffMgr instance and creates an instance if it does not exist
     * 
     * @return instance
     */
    public static StaffMgr getInstance() {
        if (instance == null) {
            instance = new StaffMgr();
        }

        return instance;
    }

    /**
     * Returns Staff object or null
     * 
     * @param staffId id of staff
     * @return Staff object if staff exists, null if staff does not exist
     */
    public Staff checkexisitingStaff(int staffId) throws IDNotFoundException {
        
        Staff staff = this.staffs.get(staffId);
        
        if(staff == null) throw new IDNotFoundException();
        return staff;
    
    }

    /**
     * Returns true or false depending if staff corresponding to the contact number
     * exists
     * 
     * @param phoneNumber contact number
     * @return true if staff exists, false if staff does not exist
     */
    public boolean checkexisitingStaff(String phoneNumber) {

        return true;
    }

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
