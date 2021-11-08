package entities;

import java.sql.Date;
import java.util.List;
import enums.TableState;

public class Table {
    private int seatingCapacity;
    private List<Date> booking;
    private TableState state;
    private int id;

    public int getSeatingCapacity() {
        return this.seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public List<Date> getBooking() {
        return this.booking;
    }

    public void setBooking(List<Date> booking) {
        this.booking = booking;
    }

    public TableState getState() {
        return this.state;
    }

    public void setState(TableState state) {
        this.state = state;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id){
        this.id=id;
    }
