package entities;

import java.time.LocalDateTime;
import java.util.HashMap;
import enums.TableState;


public class Table {
    
    Reservation res;
    private int seatingCapacity;
    private HashMap<LocalDateTime, TableState> bookings;
    private int tableId;
    
    public Table(int seatingCapacity, HashMap<LocalDateTime, TableState> bookings, int tableId){
        this.seatingCapacity = seatingCapacity;
        this.bookings = bookings;
        this.tableId = tableId;
    }

    public int getSeatingCapacity() {
        return this.seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public TableState getTableState(LocalDateTime date) {
        if (this.bookings.containsKey(date) == true)
            return this.bookings.get(date);
        else
            return TableState.AVAILABLE;
    }

    public void setTableToAvailable(LocalDateTime date) {
        this.bookings.put(date, TableState.AVAILABLE);
    }

    public void setBooking(LocalDateTime date, TableState state) {
        this.bookings.put(date, state);
    }

    public int getId() {
        return this.tableId;
    }

    public void setId(int tableId) {
        this.tableId = tableId;
    }
    // public void getAttribute(){
        
    // }
    
}
