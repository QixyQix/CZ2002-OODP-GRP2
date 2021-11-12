package managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import entities.DiscountFilter;
import entities.Membership;

public class MembershipMgr {

    private static MembershipMgr INSTANCE;
    private HashMap<Integer, Membership> membership;
    private int membershipId;
    /**
     * Constructor
     */
    private MembershipMgr() {
        this.membership = new HashMap<Integer, Membership>();
        try {
            loadSavedData();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load Membership data");
        }
    }

    /***
     * Serializes and saves the Customers objects into the data/customers folder
     * Creates the data/customers folder if it does not exist
     * 
     * @throws IOException
     */
    public void saveData() throws IOException {
        // Create directory & clear exisring data if needed
        File dataDirectory = new File("./data/membership");
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        } else {
            for (File existingData : dataDirectory.listFiles()) {
                existingData.delete();
            }
        }

        for (int id : this.membership.keySet()) {
            Membership customer = this.membership.get(id);

            FileOutputStream fileOutputStream = new FileOutputStream("./data/membership/" + id);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(customer);
            objectOutputStream.flush();
            objectOutputStream.close();
        }

        FileOutputStream fileOutputStream = new FileOutputStream("./data/membershipId");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeInt(membershipId);
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
        File dataDirectory = new File("./data/membership");
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return;

        for (File file : fileList) {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
           
            Membership membership = (Membership) objectInputStream.readObject();
            this.membership.put(membership.getId(), membership);
            objectInputStream.close();
        }
        try{
            FileInputStream fileInputStream = new FileInputStream("membershipId");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.membershipId = (int) objectInputStream.readObject();
            objectInputStream.close();
        }catch(Exception e){
            this.membershipId = 1;
        }
    }

    public Membership createMembership(DiscountFilter discountFilter) {
        Membership membership = new Membership(this.membershipId, discountFilter);
        this.membership.put(this.membershipId, membership);
        this.membershipId++;
        return membership;
    }

    /**
     * Returns the MenuItemMgr instance and creates an instance if it does not exist
     * 
     * @return
     */
    public static MembershipMgr getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MembershipMgr();
        }

        return INSTANCE;
    }
}
