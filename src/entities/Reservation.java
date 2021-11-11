package entities;

import java.time.LocalDateTime;
import java.io.Serializable;


public class Reservation implements Serializable {
    private Customer customer;
    private LocalDateTime checkInTime;
    private int noOfPax;
    private Table table;
    private int reservationID;

    public Reservation(){}

    public Reservation(Customer customer, LocalDateTime checkInTime, Integer noOfPax,Table table, int id) {
		this.customer = customer;
		this.checkInTime = checkInTime;		
		this.noOfPax = noOfPax;
		this.table = table;
        this.reservationID = id;
	}

    public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
    public LocalDateTime getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(LocalDateTime checkInTime) {
		this.checkInTime = checkInTime;
	}

    public Integer getnoOfPax() {
		return noOfPax;
	}

	public void setnoOfPax(Integer noOfPax) {
		this.noOfPax = noOfPax;
	}

    public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}


    public int getreservationID() {
		return reservationID;
	}

	public void setreservationID(int id) {
		this.reservationID = id;
	}
    // depends on CustomerMgr and TableMgr
    @Override 
    public String toString() {
        return "Reservation{" + '\n' + 
                "Customer= " + customer.getName() + '\n' +
                "customerContact= " + customer.getContact() + '\n' +
                "checkInTime= " + checkInTime.toString().replace("T", " ") + '\n' +
                "noOfPax= " + noOfPax + '\n' +
                "tableID= " + table.getId() + '\n' +
                '}';
    }
}
