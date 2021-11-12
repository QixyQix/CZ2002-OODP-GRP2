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

/***
 * Represents a table manager
 * 
 * @author Benjamin Ho JunHao
 */
public class TableMgr {
    private static TableMgr instance;
    private HashMap<Integer, Table> tables;

    private TableMgr() {
        try {
            tables = new HashMap<Integer, Table>();
            loadSavedData();
        } catch (Exception ex) {
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
     * Creates Table object
     * 
     * @param seatingCapacity the number of seats at a table
     * @param tableId         the table id of the table
     */
    public void createTable(int seatingCapacity, int tableId) {
        Table table = new Table(seatingCapacity, tableId);
        this.tables.put(table.getId(), table);
    }

    /**
     * Finds an available table Returns Table object if there exists an available
     * table Otherwise returns null
     * 
     * @param checkInTime the time that a customer checks in
     * @param noOfPax     the number of pax at the table
     * @return Table object if found, null if no table available table found
     */
    public Table findAvailTable(LocalDateTime checkInTime, int noOfPax) {
        for (Table table : tables.values()) {
            if ((table.getTableState(checkInTime) == TableState.AVAILABLE)
                    && ((table.getTableState(checkInTime.plusHours(1)) == TableState.AVAILABLE))
                    && ((table.getTableState(checkInTime.plusHours(2)) == TableState.AVAILABLE))
                    && (table.getSeatingCapacity() >= noOfPax)) {
                table.setState(checkInTime, TableState.RESERVED);
                table.setState(checkInTime.plusHours(1), TableState.RESERVED);
                table.setState(checkInTime.plusHours(2), TableState.RESERVED);
                return table;
            }
        }
        return null;
    }

    /**
     * Deallocates the table at specific time
     * 
     * @param table table to deallocate
     * @param date  date and time of the table to deallocate
     */
    public void deallocateTable(Table table, LocalDateTime date) {
        table.setTableToAvailable(date);
        table.setTableToAvailable(date.plusHours(1));
        table.setTableToAvailable(date.minusHours(1));
    }

    /**
     * Creates Table object from serialized data Returns Table object
     * 
     * @param o serialized Table object
     * @return Table object
     * @throws ClassNotFoundException if o is not of the Table class
     * 
     */
    public Table createTableFromSerializedData(Object o) throws ClassNotFoundException {
        if (o instanceof Table) {
            return (Table) o;
        } else {
            throw new ClassNotFoundException();
        }
    }

    /**
     * Prints the available tables' seating capacity and table id if there exists an
     * available table
     * 
     * @param noOfPax number of pax at the table
     * @param date    date and time of the table to check
     */
    public void printTableAvailability(int noOfPax, LocalDateTime date) {
        boolean flag = false;
        for (Table table : tables.values()) {
            if ((noOfPax < table.getSeatingCapacity()) && (table.getTableState(date) == TableState.AVAILABLE)) {
                System.out.println(table.toString());
                flag = true;
            }
        }
        if (flag == false) {
            System.out.println("There are no available tables with the specified requirements");
        }
    }

    /**
     * Prints all available tables
     * 
     */
    public void printAllTables() {
        if (tables.isEmpty()) {
            System.out.println("No tables have been created");
        } else {
            for (Table table : tables.values()) {
                System.out.println(table.toString());
            }
        }

    }

    /***
     * Serializes and saves the Table objects into the data/table folder Creates the
     * data/table folder if it does not exist
     * 
     * @throws IOException if stream to file cannot be written to or closed
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
     * @throws IOException            if stream to file cannot be written to or
     *                                closed
     * @throws ClassNotFoundException if serialized data is not of the Table class
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
