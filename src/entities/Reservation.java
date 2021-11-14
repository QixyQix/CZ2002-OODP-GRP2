package entities;

import java.time.LocalDateTime;
import java.io.Serializable;

/***
 * Represents a reservation entity
 * 
 * @author Eang Sokunthea
 * @version 1.0
 * @since 2021-11-14
 */
public class Reservation implements Serializable, Entities {
	/**
	 * The customer that book this reservation
	 */
	private Customer customer;
	/**
	 * The checkInTime of this reservation
	 */
	private LocalDateTime checkInTime;
	/**
	 * The Number of Pax of this reservation
	 */
	private int noOfPax;
	/**
	 * The table that is allocated to this reservation
	 */
	private Table table;
	/**
	 * The id of the reservation
	 */
	private int id;
	/**
	 * The check in status of this reservation (True represent check in already)
	 */
	private boolean checkInStatus;

	public Reservation() {}

	/**
	 * Constructor
	 * @param customer		Customer that book the reservation
	 * @param checkInTime	Check In Time of the reservation
	 * @param noOfPax		Number of Pax of the reservation
	 * @param table			Table allocated to the reservation
	 * @param id			id of the reservation
	 */
	public Reservation(Customer customer, LocalDateTime checkInTime, Integer noOfPax, Table table, int id) {
		this.customer = customer;
		this.checkInTime = checkInTime;
		this.noOfPax = noOfPax;
		this.table = table;
		this.id = id;
		this.checkInStatus = false;
	}

	/**
	 * Returns customer
	 * 
	 * @return customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * Sets customer
	 * 
	 * @param customer customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * Returns check in time
	 * 
	 * @return check in time
	 */
	public LocalDateTime getCheckInTime() {
		return checkInTime;
	}

	/**
	 * Sets check in time
	 * 
	 * @param checkInTime check in time
	 */
	public void setCheckInTime(LocalDateTime checkInTime) {
		this.checkInTime = checkInTime;
	}

	/**
	 * Returns number of pax
	 * 
	 * @return number of pax
	 */
	public int getnoOfPax() {
		return noOfPax;
	}

	/**
	 * Sets number of pax
	 * 
	 * @param noOfPax number of pax
	 */
	public void setnoOfPax(int noOfPax) {
		this.noOfPax = noOfPax;
	}

	/**
	 * Returns table
	 * 
	 * @return table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * Sets table
	 * 
	 * @param table table
	 */
	public void setTable(Table table) {
		this.table = table;
	}

	/**
	 * Returns id
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns check in status
	 * 
	 * @return check in status
	 */
	public boolean getCheckInStatus() {
		return checkInStatus;
	}

	/**
	 * Sets check in status
	 * 
	 * @param status check in status
	 */
	public void setCheckInStatus(boolean status) {
		this.checkInStatus = true;
	}

	/**
	 * Tostring function
	 * 
	 * @return string
	 */
    // depends on CustomerMgr and TableMgr
    @Override 
    public String toString() {
		String information = "";
		information += String.format("%-20s : %d\n", "Reservation ID" , this.getId());
		information += String.format("%-20s : %s\n", "Customer" , customer.getName());
		information += String.format("%-20s : %s\n", "Customer Contact" , customer.getContact());
		information += String.format("%-20s : %s\n", "Check In Time" , checkInTime.toString().replace("T", " "));
		information += String.format("%-20s : %d\n", "Number of Pax  " , this.noOfPax );
		information += String.format("%-20s : %d\n", "Table ID " , table.getId());
		if(this.getCheckInStatus())
			information += String.format("%-20s : %s\n", "Check In Status" , "Checked-In Succesfully" );
        else 
			information += String.format("%-20s : %s\n", "Check In Status" , "Not Yet Check-In" );    
     
		return information;
    }
}
