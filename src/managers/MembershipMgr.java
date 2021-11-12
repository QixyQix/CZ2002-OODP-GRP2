package managers;

import java.io.IOException;
import java.util.HashMap;

import entities.DiscountFilter;
import entities.Entities;
import entities.Membership;

public class MembershipMgr extends DataMgr{

    private static MembershipMgr INSTANCE;
    private HashMap<Integer, Membership> membership;
    private int nextId;
    /**
     * Constructor
     */
    private MembershipMgr() {
        this.membership = new HashMap<Integer, Membership>();
        try {
            downCast(super.loadSavedData("membership"));
            this.nextId = super.loadNextIdData("membershipNextId");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load Membership data");
        }
    }

    public void downCast(HashMap<Integer, Entities> object){
        for(int id: object.keySet()){
            if(object.get(id) instanceof Membership)
                this.membership.put(id,(Membership) object.get(id));
            else throw new ClassCastException();
        }
    }

    public  HashMap<Integer, Entities> upCast(){
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for(int id: membership.keySet()){
           object.put(id,membership.get(id)); 
        }
        return object;
    }
    
    public void saveData() throws IOException {
        saveDataSerialize(upCast(), nextId, "membership", "membershipNextId");
    }

    
    public Membership createMembership(DiscountFilter discountFilter) {
        Membership membership = new Membership(this.nextId, discountFilter);
        this.membership.put(this.nextId, membership);
        this.nextId++;
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
