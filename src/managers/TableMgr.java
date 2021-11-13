package managers;

import java.util.HashMap;
import java.io.IOException;

import entities.Entities;
import entities.Table;
import enums.TableState;
import java.time.LocalDateTime;

/***
 * Represents a table manager
 * 
 * @author Benjamin Ho JunHao
 */
public class TableMgr extends DataMgr {
    private static TableMgr INSTANCE;
    private HashMap<Integer, Table> tables;
    private int nextId;

    /**
     * Constructor
     */
    private TableMgr() {
        try {
            tables = new HashMap<Integer, Table>();
            downCast(super.loadSavedData("tables"));
            nextId = super.loadNextIdData("tableNextId");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load Tables data");
        }

    };

    /**
     * Downcast from entities to salesReportMgr
     * 
     * @param object
     */
    public void downCast(HashMap<Integer, Entities> object) {
        for (int id : object.keySet()) {
            if (object.get(id) instanceof Table)
                this.tables.put(id, (Table) object.get(id));
            else
                throw new ClassCastException();
        }
    }

    /**
     * Upcast reservationMgr to entities in a hashmap
     * 
     * @return Hashmap object
     */
    public HashMap<Integer, Entities> upCast() {
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for (int id : tables.keySet()) {
            object.put(id, tables.get(id));
        }
        return object;
    }

    /***
     * Save data
     * 
     * @throws IOException if stream to file cannot be written to or closed
     */
    public void saveData() throws IOException {
        saveDataSerialize(upCast(), nextId, "tables", "tableNextId");
    }

    /**
     * Returns the TableMgr instance and creates an instance if it does not exist
     * 
     * @return instance
     */
    public static TableMgr getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TableMgr();
        }
        return INSTANCE;
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
                    && (table.getSeatingCapacity() >= noOfPax)) {
                table.setState(checkInTime, TableState.RESERVED);
                table.setState(checkInTime.plusHours(1), TableState.RESERVED);
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
            if ((noOfPax <= table.getSeatingCapacity()) && (table.getTableState(date) != TableState.RESERVED)) {
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

}
