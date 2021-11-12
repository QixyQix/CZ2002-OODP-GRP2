package ui;

import managers.TableMgr;
import managers.ReservationMgr;
import entities.Reservation;
import entities.Customer;
import entities.Table;

import java.util.*;
import java.time.LocalDateTime;


public class ReservationUI extends UserInterface {
    private static ReservationUI singleInstance = null;
    private ReservationUI() {}

    public static ReservationUI getInstance()
    {
        if (singleInstance == null)
            singleInstance = new ReservationUI();
        return singleInstance;
    }

    private void displayOptions(){
        System.out.println("==========Reservation Manager==========");
        System.out.println("(0) Go back to Main Page");
        System.out.println("(1) Create a new reservation ");
        System.out.println("(2) Check/remove reservation booking");
        System.out.println("(3) Print Reservation");
        System.out.println("=======================================");
    }

    public void showMenu() {
        int choice;
        do{
            
            this.displayOptions();
          
            choice = super.getInputInt("Please enter your choice: ",0,3);
            
            switch (choice) {
                case 1:
                    this.createReservationUI();
                    break; 
                case 2:
                    this.checkRemoveReservationUI();
                    break;
                case 3: 
                    this.printReservationUI();
                    break;     
            } 
            super.waitEnter();
        }while(choice != 0);
    }

    
    
    private void createReservationUI() {
        Customer customer = CustomerUI.getInstance().getCustomer();
        Reservation res = ReservationMgr.getInstance().checkReservation(customer);	
        if(res != null) {
            printReservation(res);
            return;
        }        

        System.out.println("Please fill in your requirements: ");
	    LocalDateTime checkInTime = super.getInputDateTime("Check In time (yyyy-MM-dd HH:mm): ");
	    int noOfPax = super.getInputInt("Number Of people: ");

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
    
    private void checkRemoveReservationUI(){
        Customer customer = CustomerUI.getInstance().getCustomer();
        Reservation res = ReservationMgr.getInstance().checkReservation(customer);	
        
        //May change if YanKai want it to be check first then only take O(2N) tho;
        // TODO  this actually also can,t catch error unless we throw ourself.
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
    }   
}
