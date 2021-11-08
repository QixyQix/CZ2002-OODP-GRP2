package managers;

import java.util.HashMap;

import entities.Table;
import enums.TableState;
import java.time.LocalDateTime;

public class TableMgr {
    private static TableMgr instance = null;
    private HashMap<Integer, Table> tables;
    private int tableID;

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
        table.setTableState(date);
    }
}
