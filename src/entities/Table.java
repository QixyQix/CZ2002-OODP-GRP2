package entities;

import java.sql.Date;
import java.util.List;
import enums.TableState;



public class Table {
    Reservation res;
    private int seatingCapacity;
    private List<res.date> booking;
    private TableState state;
    private String id;
    
    public void getSeatingCapacity(){
        return this.seatingCapacity
    }
    public void setSeatingCapacity(int seatingCapacity){
        this.seatingCapacity=seatingCapacity;
    }
    // public ReservationMgr[] getDate(){
        
    // }
    // public void setDate(ReservationMgr){
        
    // }
    public List getBooking(){
        
    }
    public void setBooking(List booking){
        
    }
    public TableState getState(){
        return this.state;
    }
    public void setState(TableState state){
        this.state=state;
    }
    public String getId(){
        return this.id;
    }
    public void setId(String id){
        this.id=id;
    }
    // public void getAttribute(){
        
    // }
}
