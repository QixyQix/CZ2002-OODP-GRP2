package managers;

import java.util.HashMap;

import entities.Table;
import enums.TableState;
import java.time.LocalDateTime;

public class TableMgr {
    private static TableMgr instance;
    private HashMap<Integer, Table> tables;

    private TableMgr() {
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

    public void createTable(int seatingCapacity, HashMap<LocalDateTime, TableState> bookings, int tableId) {
        Table table = new Table(seatingCapacity, bookings, tableId);
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
            if (table.getTableState(checkInTime) == TableState.AVAILABLE) {
                table.setBooking(checkInTime, TableState.RESERVED);
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
    }

    /**
     * Returns the true if there is an available table for the number of pax at that
     * time Otherwise returns false
     * 
     * @return true or false
     */
    public boolean checkTableAvailability(int noOfPax, LocalDateTime date) {
        for (Table table : tables.values()) {
            if ((noOfPax < table.getSeatingCapacity()) && (table.getTableState(date) == TableState.AVAILABLE)) {
                return true;
            }
        }
        return false;
    }
}
