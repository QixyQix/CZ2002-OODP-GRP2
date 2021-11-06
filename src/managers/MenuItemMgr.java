package managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import entities.MenuItem;
import exceptions.DuplicateIDException;
import exceptions.IDNotFoundException;
import factories.MenuItemFactory;

public final class MenuItemMgr {
    private static MenuItemMgr INSTANCE;
    private HashMap<Integer, MenuItem> items;

    /**
     * Constructor
     */
    private MenuItemMgr() {
        this.items = new HashMap<Integer, MenuItem>();
        try {
            loadSavedData();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load menu data");
        }
    }

    /**
     * Returns the MenuItemMgr instance and creates an instance if it does not exist
     * @return
     */
    public static MenuItemMgr getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MenuItemMgr();
        }

        return INSTANCE;
    }

    public void createMenuItem(String type, String name, String description, double price, int id, ArrayList<MenuItem> packageItems) throws DuplicateIDException {
        if (this.items.containsKey(id)) {
            throw new DuplicateIDException();
        }

        try {
            MenuItem newItem = MenuItemFactory.getInstance().createMenuItem(type, name, description, price, id, packageItems);

            this.items.put(newItem.getId(), newItem);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    /**
     * Checks if an ID is available to be used
     * Returns true if ID can be assigned to a new object, false otherwise
     * @param id
     * @return
     */
    public boolean checkIDAvailable(int id){
        return !this.items.containsKey(id);
    }

    /**
     * Returns a MenuItem object that that matches the ID
     * @param id
     * @return
     * @throws IDNotFoundException
     */
    public MenuItem getMenuItemByID(int id) throws IDNotFoundException {
        if (!this.items.containsKey(id)) {
            throw new IDNotFoundException();
        }

        return this.items.get(id);
    }

    /***
     * Returns an array of MenuItem objects that are stored in the MenuItemMgr
     * @return
     * @throws IDNotFoundException
     */
    public MenuItem[] getAllMenuItems() throws IDNotFoundException{
        MenuItem[] items = new MenuItem[this.items.size()];
        
        int i = 0;
        for(int id:this.items.keySet()){
            items[i] = getMenuItemByID(id);
        }

        return items;
    }

    /***
     * Serializes and saves the MenuItem objects into the data/menuItems folder
     * Creates the data/menuItems folder if it does not exist
     * @throws IOException
     */
    public void saveData() throws IOException {
        //Create directory & clear exisring data if needed
        File dataDirectory = new File("./data/menuItems");
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        }else{
            for(File existingData : dataDirectory.listFiles()){
                existingData.delete();
            }
        }

        for (int itemId : items.keySet()) {
            MenuItem item = items.get(itemId);

            FileOutputStream fileOutputStream = new FileOutputStream("./data/menuItems/" + itemId);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(item);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
    }

    /***
     * Reads Serialized MenuItem data in the data/menuItems folder and stores it into the items HashMap
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadSavedData() throws IOException, ClassNotFoundException {
        File dataDirectory = new File("./data/menuItems");
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return;

        for (File file : fileList) {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            MenuItem item = MenuItemFactory.getInstance()
                    .createMenuItemFromSerializedData(objectInputStream.readObject());
            this.items.put(item.getId(), item);
            objectInputStream.close();
        }
    }
}
