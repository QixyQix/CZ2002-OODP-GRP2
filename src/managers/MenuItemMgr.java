package managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import entities.MenuItem;
import enums.MenuItemState;
import exceptions.DuplicateIDException;
import exceptions.IDNotFoundException;
import factories.MenuItemFactory;

public final class MenuItemMgr {
    private static MenuItemMgr INSTANCE;
    private HashMap<Integer, MenuItem> items;

    private MenuItemMgr() {
        this.items = new HashMap<Integer, MenuItem>();
        try {
            loadSavedData();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load menu data");
        }
    }

    public static MenuItemMgr getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MenuItemMgr();
        }

        return INSTANCE;
    }

    public void createMenuItem(String type, String name, String description, double price, int stocks,
            MenuItemState state, int id, MenuItem[] packageItems) throws DuplicateIDException {
        if (this.items.containsKey(id)) {
            throw new DuplicateIDException();
        }

        try {
            MenuItem newItem = MenuItemFactory.getInstance().createMenuItem("ALACARTE", type, name, description, price,
                    stocks, state, id, packageItems);

            this.items.put(newItem.getId(), newItem);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public MenuItem getMenuItemByID(int id) throws IDNotFoundException {
        if (!this.items.containsKey(id)) {
            throw new IDNotFoundException();
        }

        return this.items.get(id);
    }

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
