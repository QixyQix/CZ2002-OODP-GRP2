package ui;

import managers.TableMgr;
import managers.ReservationMgr;
import managers.CustomerMgr;
import entities.Reservation;
import entities.Customer;
import entities.Table;

import java.util.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ReservationUI extends UserInterface {
    private static ReservationUI singleInstance = null;
    private static Scanner sc = new Scanner(System.in);
    private ReservationUI() {}

    public static ReservationUI getInstance()
    {
        if (singleInstance == null)
            singleInstance = new ReservationUI();
        return singleInstance;
    }


    public void selectOption() {
        String customerContact;
        while(true){
            System.out.println("0. Go back to Main Page");
            System.out.println("1. Create a new reservation ");
            System.out.println("2. Check/remove reservation booking");
            System.out.println("3. Print Reservation");
          
            int choice = super.getInputInt("Please enter your choice: ", 0,3);
            
            switch (choice) {
                case 0: 
                    super.waitEnter();
                    return;
                case 1:
                    customerContact = getContact("Please enter customer contact: ");
                    this.createReservationUI(customerContact);
                    break; 
                case 2:   
                    customerContact = getContact("Please enter customer contact: ");
                    this.checkRemoveReservationUI(customerContact);
                    break;
                        
                case 3: printReservationUI();
                    break;
                default: System.out.println("Invalid Choice");
                    break;

            } 
        }
    }

    private void printReservation(Reservation res){
        System.out.println("There is a reservation made by the customer");
        System.out.println(res.toString());
        System.out.println();
    }
    
    private void createReservationUI(String customerContact) {
        //should we check whether this customer have reservation or not?? Since we allow one custoemr to have one reservation..
        Reservation res = ReservationMgr.getInstance().checkReservation(customerContact);	
        if(res != null) {
            printReservation(res);
            super.waitEnter();
            return;
        }
        // Up to here is edited by ZY, not sure yet, need confirmation..

        System.out.println("Please fill in your requirements: ");
        
	    LocalDateTime checkInTime = super.getInputDateTime("Check In time (yyyy-MM-dd HH:mm): ");
	    int noOfPax = super.getInputInt("Number Of people: ");

        Table table = TableMgr.getInstance().findAvailTable(checkInTime, noOfPax);
        if (table==null){ 
            System.out.println("There is no available table with the specified requirements.");
        }
        else {
            System.out.println("We have an available table. But first we need your particulars.");
            // depends on yk and zong yu to create CustomerMgr + findExistingCustomer(if dont have must create) method
            Customer customer = CustomerMgr.getInstance().getExistingCustomer(customerContact);
            res = ReservationMgr.getInstance().createReservation(customer, checkInTime, noOfPax, table);
            System.out.println("New reservation added to the system: ");
	        System.out.println(res.toString());
        }
        super.waitEnter();
    }

    private void checkRemoveReservationUI(String customerContact){
        Reservation res = ReservationMgr.getInstance().checkReservation(customerContact);	
        
        //May change if YanKai want it to be check first then only take O(2N) tho;
        if(res == null){
            System.out.println("There is no reservation made by the customer");
            super.waitEnter();
            return;
        }

		this.printReservation(res);
        
        if (super.getYNOption("Would you like to remove this reservation? [yes/no]") ){
            ReservationMgr.getInstance().removeReservation(res);
            System.out.println("Reservation successfully removed from the system: ");
        }
        super.waitEnter();
    }
    private void printReservationUI(){
        HashMap<Integer, Reservation> reservations = ReservationMgr.getInstance().getAllReservations();
		if (reservations.isEmpty()) {
        	System.out.println("No records found!");
        }
		else {
			System.out.println("Printing all reservations in the system record...");
			for(Reservation reservation: reservations.values()) {
				System.out.println(reservation.toString());
				System.out.print('\n');
			}
		}
        super.waitEnter();
    }   
}
