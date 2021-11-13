package entities;

import java.time.LocalDateTime;
import java.io.Serializable;


public class Reservation implements Serializable, Entities {
    private Customer customer;
    private LocalDateTime checkInTime;
    private int noOfPax;
    private Table table;
    private int id;
	private boolean checkInStatus;

    public Reservation(){}

    public Reservation(Customer customer, LocalDateTime checkInTime, Integer noOfPax,Table table, int id) {
		this.customer = customer;
		this.checkInTime = checkInTime;		
		this.noOfPax = noOfPax;
		this.table = table;
        this.id = id;
		this.checkInStatus = false;
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


    public int getId() {
		return id;
	}



	public boolean getCheckInStatus() {
		return checkInStatus;
	}

	public void setCheckInStatus(boolean status) {
		this.checkInStatus = true;
	}
    // depends on CustomerMgr and TableMgr
    @Override 
    public String toString() {
        return  "\n" +
				"Reservation ID= " + this.getId()+ '\n' +
                "Customer= " + customer.getName() + '\n' +
                "customerContact= " + customer.getContact() + '\n' +
                "checkInTime= " + checkInTime.toString().replace("T", " ") + '\n' +
                "noOfPax= " + noOfPax + '\n' +
                "tableID= " + table.getId() + '\n' +
				"Check In= " + getCheckInStatus()+ '\n' +
                '\n';
    }
}
