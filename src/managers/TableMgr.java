package managers;

import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import entities.Table;
import enums.TableState;
import java.time.LocalDateTime;

public class TableMgr {
    private static TableMgr instance;
    private HashMap<Integer, Table> tables;

    private TableMgr() {
        try{
            tables = new HashMap<Integer, Table>();
            loadSavedData();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            System.out.println("Failed to load Tables data");
        }
        
    };

    /**
     * Returns the TableMgr instance and creates an instance if it does not exist
     * 
     * @return instance
     */
    public static TableMgr getInstance() {
        if (instance == null) {
            instance = new TableMgr();
        }
        return instance;
    }

    /**
     * Creates table object
     * 
     * @param seatingCapacity, bookings, tableId
     * @return
     */
    public void createTable(int seatingCapacity, int tableId) {
        Table table = new Table(seatingCapacity, tableId);
        this.tables.put(table.getId(), table);
    }

    /**
     * Finds an available table Returns table if there exists an available table
     * Otherwise returns null
     * 
     * @param checkInTime, noOfPax
     * @return table or null
     */
    public Table findAvailTable(LocalDateTime checkInTime, int noOfPax) {
        for (Table table : tables.values()) {
            if ((table.getTableState(checkInTime) == TableState.AVAILABLE) && (table.getSeatingCapacity() >= noOfPax)) {
                table.setState(checkInTime, TableState.RESERVED);
                table.setState(checkInTime.plusHours(1), TableState.RESERVED);
                table.setState(checkInTime.minusHours(1), TableState.RESERVED);
                return table;
            }
        }
        return null;
    }

    /**
     * Deallocates the table at specific time
     * 
     * @param table, date
     * @return
     */
    public void deallocateTable(Table table, LocalDateTime date) {
        table.setTableToAvailable(date);
        table.setTableToAvailable(date.plusHours(1));
        table.setTableToAvailable(date.minusHours(1));
    }

    /**
     * Creates table object from serialized data and returns a table
     * 
     * @param o
     * @return Table
     */
    public Table createTableFromSerializedData(Object o) throws ClassNotFoundException {
        if (o instanceof Table) {
            return (Table) o;
        } else {
            throw new ClassNotFoundException();
        }
    }

    /**
     * Returns the true if there is an available table for the number of pax at that
     * time Otherwise returns false
     * 
     * @return true or false
     */
    public void checkTableAvailability(int noOfPax, LocalDateTime date) {
        for (Table table : tables.values()) {
            if ((noOfPax < table.getSeatingCapacity()) && (table.getTableState(date) == TableState.AVAILABLE)) {
                System.out.println(table.toString());
            }
        }
    }

    /***
     * Serializes and saves the Table objects into the data/table folder Creates the
     * data/table folder if it does not exist
     * 
     * @throws IOException
     */
    public void saveData() throws IOException {
        // Create directory & clear exisring data if needed
        File dataDirectory = new File("./data/table");
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        } else {
            for (File existingData : dataDirectory.listFiles()) {
                existingData.delete();
            }
        }

        for (int tableId : tables.keySet()) {
            Table table = tables.get(tableId);

            FileOutputStream fileOutputStream = new FileOutputStream("./data/table/" + tableId);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(table);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
    }

    /***
     * Reads Serialized Table data in the data/table folder and stores it into the
     * tables HashMap
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadSavedData() throws IOException, ClassNotFoundException {
        File dataDirectory = new File("./data/table");
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return;

        for (File file : fileList) {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Table table = this.createTableFromSerializedData(objectInputStream.readObject());
            this.tables.put(table.getId(), table);
            objectInputStream.close();
        }
    }
}
