package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import enums.TableState;

/***
 * Represents a report entity
 * 
 * @author Benjamin Ho JunHao
 * @version 1.0
 * @since 2021-11-14
 */
public class Table implements Serializable, Entities {
    /**
     * The seating Capacity of this table
     */
    private int seatingCapacity;
    /**
     * The Bookings contains the Date and its TableState (Available/Reserved) of this table
     */
    private HashMap<LocalDateTime, TableState> bookings;
    /**
     * The id of this Table
     */
    private int id;

    /**
     * Constructor
     */
    public Table() {
    }

    /**
     * Constructor
     * @param seatingCapacity   Seating Capacity of the table
     * @param id                Id of the table
     */
    public Table(int seatingCapacity, int id) {
        this.seatingCapacity = seatingCapacity;
        this.bookings = new HashMap<LocalDateTime, TableState>();
        this.id = id;
    }

    /**
     * Returns seating capacity
     * 
     * @return seating capacity
     */
    public int getSeatingCapacity() {
        return this.seatingCapacity;
    }

    /**
     * Sets seating capacity
     * 
     * @param seatingCapacity seating capacity
     */
    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    /**
     * Returns table state
     * 
     * @return table state
     */
    public TableState getTableState(LocalDateTime date) {
        if (this.bookings.containsKey(date))
            return this.bookings.get(date);
        else
            return TableState.AVAILABLE;
    }

    /**
     * Sets table state to available at specified date
     * 
     * @param date date
     */
    public void setTableToAvailable(LocalDateTime date) {
        this.bookings.put(date, TableState.AVAILABLE);
    }

    /**
     * Sets table state at specified date
     * 
     * @param date  date
     * @param state table state
     */
    public void setState(LocalDateTime date, TableState state) {
        this.bookings.put(date, state);
    }

    /**
     * Returns id
     * 
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Tostring function
     * 
     * @return string
     */
    public String toString() {
        return String.format("Table ID = %-3d| Seating Capacity = %-2d", this.getId(), this.getSeatingCapacity());
    }
}
