package managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import entities.Staff;
import exceptions.IDNotFoundException;

/***
 * Represents a staff manager
 * 
 * @author Zong Yu Lee
 */
public final class StaffMgr {
    private static StaffMgr instance;
    private HashMap<Integer, Staff> staffs;
    private int staffId; // will be delete afterward

    private StaffMgr() {
        try {
            this.staffs = new HashMap<Integer, Staff>();
            loadSavedData();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load staffs data");
        }
    }

    /***
     * Serializes and saves the Staffs objects into the data/staffs folder Creates
     * the data/staffs folder if it does not exist
     * 
     * @throws IOException if stream to file cannot be written to or closed
     */
    public void saveData() throws IOException {
        // Create directory & clear exisring data if needed
        File dataDirectory = new File("./data/staffs");
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        } else {
            for (File existingData : dataDirectory.listFiles()) {
                existingData.delete();
            }
        }

        for (int staffID : this.staffs.keySet()) {
            Staff staff = this.staffs.get(staffID);

            FileOutputStream fileOutputStream = new FileOutputStream("./data/staffs/" + staffID);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(staff);
            objectOutputStream.flush();
            objectOutputStream.close();
        }

        FileOutputStream fileOutputStream = new FileOutputStream("./data/staffId");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeInt(staffId);
        objectOutputStream.flush();
        objectOutputStream.close();

    }

    /***
     * Reads Serialized MenuItem data in the data/menuItems folder and stores it
     * into the items HashMap
     * 
     * @throws IOException            if stream to file cannot be written to or
     *                                closed
     * @throws ClassNotFoundException if serialized data is not of the Customer
     *                                class
     */
    private void loadSavedData() throws IOException, ClassNotFoundException {
        File dataDirectory = new File("./data/staffs");
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return;

        try {
            File file = new File("./data/staffId");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.staffId = (int) objectInputStream.readInt();
            objectInputStream.close();
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            this.staffId = 1;
        }

        for (File file : fileList) {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Staff staff = (Staff) objectInputStream.readObject();
            this.staffs.put(staff.getId(), staff);
            objectInputStream.close();
        }

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
        Staff staff = new Staff(staffId, jobTitle, name, gender, contact);
        this.staffs.put(staffId, staff);
        this.staffId++;
        return staff;
    }

}
