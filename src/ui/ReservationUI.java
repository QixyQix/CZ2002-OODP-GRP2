package ui;

import managers.TableMgr;
import managers.ReservationMgr;
import managers.CustomerMgr;
import entities.Reservation;
import entities.Customer;
import entities.Table;
import enums.TableState;

import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ReservationUI {
    private static ReservationUI singleInstance = null;
    private ReservationMgr reservationMgr = ReservationMgr.getInstance();
    private TableMgr tableMgr = TableMgr.getInstance();
    private CustomerMgr customerMgr = CustomerMgr.getInstance();
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
        System.out.println("0. Go back to Main Page");
        System.out.println("1. Create a new reservation ");
        System.out.println("2. Check/remove reservation booking");
        System.out.println("3. Print Reservation");
        System.out.println("Please enter your choice: ");
        int choice = sc.nextInt();
        
        switch (choice) {
            case 0: break;
            case 1:
                    {
                        System.out.print("Please enter customer contact: ");
                        customerContact = sc.nextLine().trim().replace(" ", "");
                        createReservationUI(customerContact);
                        break;
                    } 
            case 2: 
                    {
                        System.out.print("Please enter customer contact: ");
                        customerContact = sc.nextLine().trim().replace(" ", "");
                        checkRemoveReservationUI(customerContact);
                        break;
                    }
            case 3: printReservationUI();
                    break;
            default: System.out.println("Invalid Choice");
                     break;

        } while (choice > 0);
    }

    private void createReservationUI(String customerContact){
        System.out.println("Please fill in your requirements: ");
        System.out.print("Check In time (yyyy-MM-dd HH:mm): ");
	    String checkIn = sc.nextLine().trim().replace(" ", "T");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");//input format:yyyy-mm-ddThh:mm	
    	LocalDateTime checkInTime = LocalDateTime.parse(checkIn, formatter);
        sc.nextLine();
        System.out.print("Number Of people: ");
	    int noOfPax = sc.nextInt();
        sc.nextLine();

        //depends on ben to create TableMgr + findAvailableTable Method
        Table table = tableMgr.findAvailableTable(checkInTime, noOfPax);
        if (table==null){ System.out.println("There is no available table with the specified requirements.");}
        else {
            System.out.println("We have an available table. But first we need your particulars.");

            // depends on yk and zong yu to create CustomerMgr + findExistingCustomer(if dont have must create) method
            Customer customer = customerMgr.findExistingCustomer(customerContact);
            Reservation res = reservationMgr.createReservation(customer, checkInTime, noOfPax, table);
            System.out.println("New reservation added to the system: ");
	        System.out.println(res.toString());
        }
    }

    private void checkRemoveReservationUI(String customerContact){
        Reservation res = reservationMgr.checkReservation(customerContact);			        
		System.out.println("Reservation made: ");
		System.out.println(res.toString());
        sc.nextLine();
        System.out.print("Would you like to remove this reservation? [yes/no]");
	    String remove = sc.nextLine();
        if (remove == "yes" || remove =="Yes"){
            reservationMgr.removeReservation(res);
            Table table = res.getTable();
            tableMgr.deallocateTable(table, TableState.AVAILABLE);
            System.out.println("Reservation successfully removed from the system: ");

        }
		return;	
    }
    private void printReservationUI(){
        HashMap<Integer, Reservation> reservations = reservationMgr.getAllReservations();
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
