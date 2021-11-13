package managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import entities.Entities;

/***
 * Represents a data manager
 * 
 * @author Zong Yu Lee
 */
public abstract class DataMgr {

    /***
     * Reads Serialized Customer data in the data/customers folder and stores it
     * into the customers HashMap
     * 
     * @throws IOException            if stream to file cannot be written to or
     *                                closed
     * @throws ClassNotFoundException if serialized data is not of the Customer
     *                                class
     */
    public HashMap<Integer, Entities> loadSavedData(String name_o) throws IOException, ClassNotFoundException {
        HashMap<Integer, Entities> entities = new HashMap<Integer, Entities>();
        File dataDirectory = new File("./data/" + name_o);
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return entities;

        for (File file : fileList) {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Entities entity = (Entities) objectInputStream.readObject();
            entities.put(entity.getId(), entity);
            objectInputStream.close();
        }
        return entities;
    }

    /***
     * Loads the next ID to be used when creating entities
     * 
     * @return nextId
     */
    public int loadNextIdData(String name_id) {
        int nextId;
        try {
            FileInputStream fileInputStream = new FileInputStream("./data/" + name_id);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            nextId = (int) objectInputStream.readInt();
            objectInputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            nextId = 1;
        }
        return nextId;
    }

    /***
     * Upcast respective entity subclasses to entities superclass
     * 
     * @return Hashmap
     */
    public abstract HashMap<Integer, Entities> upCast();

    /***
     * Downcast respective entities superclasses to entity subclass
     * 
     */
    public abstract void downCast(HashMap<Integer, Entities> object);

    /***
     * Save data
     * 
     * @throws IOException if stream to file cannot be written to or closed
     */
    public abstract void saveData() throws IOException;

    /***
     * Serializes and saves the Customers objects into the data/customers folder
     * Creates the data/customers folder if it does not exist
     * 
     * @throws IOException if stream to file cannot be written to or closed
     */
    public void saveDataSerialize(HashMap<Integer, Entities> entities, int nextId, String name_o, String name_id)
            throws IOException {
        // Create directory & clear exisring data if needed
        File dataDirectory = new File("./data/" + name_o);
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        } else {
            for (File existingData : dataDirectory.listFiles()) {
                existingData.delete();
            }
        }

        for (int id : entities.keySet()) {
            Object customer = entities.get(id);

            FileOutputStream fileOutputStream = new FileOutputStream("./data/" + name_o + "/" + id);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(customer);
            objectOutputStream.flush();
            objectOutputStream.close();
        }

        FileOutputStream fileOutputStream = new FileOutputStream("./data/" + name_id);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeInt(nextId);
        objectOutputStream.flush();
        objectOutputStream.close();

    }
}
