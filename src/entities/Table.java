package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import enums.TableState;

public class Table implements Serializable,Entities {
    private int seatingCapacity;
    private HashMap<LocalDateTime, TableState> bookings;
    private int id;

    public Table() {
    }

    public Table(int seatingCapacity, int id) {
        this.seatingCapacity = seatingCapacity;
        this.bookings = new HashMap<LocalDateTime, TableState>();
        this.id = id;
    }

    public int getSeatingCapacity() {
        return this.seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public TableState getTableState(LocalDateTime date) {
        if (this.bookings.containsKey(date))
            return this.bookings.get(date);
        else
            return TableState.AVAILABLE;
    }

    public void setTableToAvailable(LocalDateTime date) {
        this.bookings.put(date, TableState.AVAILABLE);
    }

    public void setState(LocalDateTime date, TableState state) {
        this.bookings.put(date, state);
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        return "\n" + "Table ID = " + getId() + '\n' + "Seating Capacity = " + getSeatingCapacity() + '\n';
    }
}
