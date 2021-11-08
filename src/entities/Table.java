package entities;

import java.time.LocalDateTime;
import java.util.List;
import enums.TableState;

public class Table {
    private int seatingCapacity;
    private List<LocalDateTime> booking;
    private TableState state;
    private int tableId;

    public int getSeatingCapacity() {
        return this.seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public List<LocalDateTime> getBooking() {
        return this.booking;
    }

    public void setBooking(List<LocalDateTime> booking) {
        this.booking = booking;
    }

    public TableState getState() {
        return this.state;
    }

    public void setState(TableState state) {
        this.state = state;
    }

    public int getId() {
        return this.tableid;
    }

    public void setId(int tableId){
        this.tableId=tableId;
    }
