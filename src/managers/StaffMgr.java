package managers;
import java.util.HashMap;
import entities.Staff;

public final class StaffMgr {
    private static StaffMgr INSTANCE;
    private HashMap<Integer, Staff> staffs ;
    private int staffId=1; // will be delete afterward

    private void loadSavedData(){
        this.staffs = new HashMap<Integer, Staff>();
    }
    
    public void saveData(){

    }
    private StaffMgr() {
        loadSavedData();
    }

    /**
     * Returns the MenuItemMgr instance and creates an instance if it does not exist
     * @return
     */
    public static StaffMgr getInstance() {
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
