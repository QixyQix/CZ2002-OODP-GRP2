package entities;

import java.time.LocalDateTime;

public class Reservation {
    private Customer customer;
    private LocalDateTime checkInTime;
    private int noOfPax;
    private Table table;
    private boolean expired;

    public Reservation(Customer customer, LocalDateTime checkInTime, Integer noOfPax,Table table) {
		this.customer = customer;
		this.checkInTime = checkInTime;		
		this.noOfPax = noOfPax;
		this.table = table;
		this.expired = false;	
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

    public boolean getExpired() {
		return expired;
	}

	public void setExpired(Boolean status) {
		this.expired = status;
	}
    
    @Override
    public String toString() {
        return "Reservation{" + '\n' + 
                "Customer= " + customer.getName() + '\n' +
                "customerContact= " + customer.getContact() + '\n' +
                "checkInTime= " + checkInTime.toString().replace("T", " ") + '\n' +
                "noOfPax= " + noOfPax + '\n' +
                "tableID= " + table.getTableID() + '\n' +
                '}';
    }
}
