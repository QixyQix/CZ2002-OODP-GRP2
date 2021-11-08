package managers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import entities.Staff;

public final class StaffMgr {
    private static StaffMgr INSTANCE;
    private HashMap<Integer, Staff> staffs;
    private int staffId; // will be delete afterward

    /**
     * Constructor
     */
    private StaffMgr(){
        try {
            this.staffs = new HashMap<Integer,Staff>();
            loadSavedData();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load staffs data");
        }
    }

     /***
     * Serializes and saves the Staffs objects into the data/staffs folder
     * Creates the data/staffs folder if it does not exist
     * 
     * @throws IOException
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
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void loadSavedData() throws IOException, ClassNotFoundException {
        File dataDirectory = new File("./data/staffs");
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return;

        try{
            File file = new File("./data/staffId");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.staffId = (int) objectInputStream.readInt();
            objectInputStream.close();
        }catch(Exception e){
            //System.out.println(e.getMessage());
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
     * @return
     */
    public static StaffMgr getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new StaffMgr();
        }

        return INSTANCE;
    }

    public Staff checkexisitingStaff(int id){
        Staff staff = null;

        if(this.staffs.containsKey(id)) staff = this.staffs.get(id);


        return staff;
    }

    public boolean checkexisitingStaff(String phoneNumber){
        
        return true;
    }

    public Staff createStaff(String jobTitle, String name, String gender, String contact ){
        Staff staff = new Staff(staffId, jobTitle, name, gender, contact);
        this.staffs.put(staffId,staff);
        this.staffId++;
        return staff;
    }

    


}
