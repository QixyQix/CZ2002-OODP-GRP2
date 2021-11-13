package ui;

import managers.TableMgr;
import managers.OrderMgr;
import managers.ReservationMgr;
import entities.Reservation;
import entities.Customer;
import entities.Table;

import java.util.*;
import java.time.LocalDateTime;


public class ReservationUI extends UserInterface {
    private static ReservationUI INSTANCE;
    private ReservationUI() {}

    public static ReservationUI getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new ReservationUI();
        return INSTANCE;
    }

    private void displayOptions(){
        System.out.println("==========Reservation Manager==========");
        System.out.println("(0) Go back to Main Page");
        System.out.println("(1) Create a new reservation ");
        System.out.println("(2) Check/remove reservation booking");
        System.out.println("(3) Print All Reservation");
        System.out.println("(4) Check in");
        System.out.println("=======================================");
    }

    public void showSelection() {
        int choice;
        do{
            
            this.displayOptions();
          
            choice = super.getInputInt("Please enter your choice: ",0,4);
            
            switch (choice) {
                case 1:
                    this.createReservationUI();
                    break; 
                case 2:
                    this.checkRemoveReservationUI();
                    break;
                case 3: 
                    this.printAllReservationUI();
                    break;  
                case 4: 
                    this.checkInUI();
                    break;    
            } 
            super.waitEnter();
        }while(choice != 0);
    }

    
    
    private void createReservationUI() {
        Customer customer = CustomerUI.getInstance().getCustomer();
        Reservation res = ReservationMgr.getInstance().checkReservation(customer);	
        if(res != null) {
            System.out.println("Ongoing reservation (You are not allowed to make more than 1 reservation at a time):");
            this.printReservation(res);
            return;
        }        

        System.out.println("Please fill in your requirements: ");
	    LocalDateTime checkInTime = super.getInputDateTime("Check In time (yyyy-MM-dd HH:mm): ");
        LocalDateTime current = LocalDateTime.now();
        while (checkInTime.isBefore(current)){
            System.out.println("Please input a valid future date.");
            checkInTime = super.getInputDateTime("Check In time (yyyy-MM-dd HH:mm): ");
        }

	    int noOfPax = super.getInputInt("Number of Pax: ");

        Table table = TableMgr.getInstance().findAvailTable(checkInTime, noOfPax);
        if (table==null){ 
            System.out.println("There is no available table with the specified requirements.");
        }
        else {
            System.out.println("We have an available table. We can reserve a table for you");
            if (!super.getYNOption("Do you confirm the reservation")) {
                return;
            }
            res = ReservationMgr.getInstance().createReservation(customer, checkInTime, noOfPax, table);
            System.out.println("New reservation added to the system: ");
	        System.out.println(res.toString());
        }
    }

    private void printReservation(Reservation res){
        System.out.println("There is a reservation made by the customer");
        System.out.println(res.toString());
        System.out.println();
    }

    private void checkInUI(){
        Customer customer = CustomerUI.getInstance().getCustomer();
        Reservation res = ReservationMgr.getInstance().checkReservation(customer);	
        
        if(res == null){
            System.out.println("There is no reservation made by the customer");
            return;
        }

		this.printReservation(res);
        
        if (res.getCheckInTime().isBefore(LocalDateTime.now())){
            if (super.getYNOption("Would you like to check in now?")){
                res.setCheckInStatus(true);
                OrderMgr.getInstance().createOrder(super.getStaff(), customer, res.getCheckInTime(), res.getnoOfPax(), res.getTable());
            }
        }
        else{
            System.out.println("The current time now is "+ LocalDateTime.now() );
            System.out.println("Sorry please wait until "+res.getCheckInTime()+" to check in");
        }
    }
    
    private void checkRemoveReservationUI(){
        Customer customer = CustomerUI.getInstance().getCustomer();
        Reservation res = ReservationMgr.getInstance().checkReservation(customer);	
        
        if(res == null){
            System.out.println("There is no reservation made by the customer");
            return;
        }

		this.printReservation(res);
        
        if (super.getYNOption("Would you like to remove this reservation?") ){
            ReservationMgr.getInstance().removeReservation(res);
            System.out.println("Reservation successfully removed from the system: ");
        }
    }

    private void printAllReservationUI(){
        HashMap<Integer, Reservation> reservations = ReservationMgr.getInstance().getAllReservations();
		if (reservations.isEmpty()) {
        	System.out.println("No records found!");
        }
		else {
			System.out.println("\n"+ "Printing all reservations in the system record...");
			for(Reservation reservation: reservations.values()) {
                System.out.println("_____________________________________________");
				System.out.println(reservation.toString());

			}
		}
    }   
}
