package boundary;

import managers.TableMgr;
import managers.ReservationMgr;
import entities.Reservation;
import entities.Customer;
import entities.Table;

import java.io.*;
import java.util.*;

import javax.swing.plaf.TableUI;

public class ReservationUI {
    private static ReservationUI singleInstance = null;
    private ReservationMgr reservationMgr = ReservationMgr.getInstance();
    private static Scanner sc = new Scanner(System.in);
    private ReservationUI() {}

    public static ReservationUI getInstance()
    {
        if (singleInstance == null)
            singleInstance = new ReservationUI();
        return singleInstance;
    }


    public void selectOption() {
        System.out.println("0. Go back to Main Page");
        System.out.println("1. Create a new reservation ");
        System.out.println("2. Check reservation booking");
        System.out.println("3. Remove reservation booking");
        System.out.println("4. Print Reservation");
        System.out.println("Please enter your choice: ");
        int choice = sc.nextInt();
        
        switch (choice) {
            case 0: break;
            case 1: createReservationUI();
                    break;
            case 2: checkReservationUI();
                    break;
            case 3: removeReservationUI();
                    break;
            case 4: printReservationUI();
                    break;
            default: System.out.println("Invalid Choice");
                     break;

        } while (choice > 0);
    }

    private void createReservationUI( String customerContact){
        System.out.println("Please fill in your requirements: ");
        System.out.print("Check In time (yyyy-MM-dd HH:mm): ");
	    String checkIn = sc.nextLine().trim().replace(" ", "T");
        sc.nextLine();
        System.out.print("Number Of people: ");
	    int noOfPax = sc.nextInt();
        sc.nextLine();

        TableUI tableui = TableUI.getInstance();
        CustomerUI customerui = CustomerUI.getInstance();
        Customer customer = customerui.findCustomerUI(customerContact);
        Table table = tableui.findTableUI(checkIn, noOfPax);
        if (table==null){ System.out.println("There is no available table with the specified requirements.");}
        else {
            System.out.println("We have an available table. Please fill in your reservation details");
            Reservation res = ReservationMgr.createReservation(customer, checkIn, noOfPax, table);
            System.out.println("New reservation added to the system: ");
	        System.out.println(res.toString());
        }
    }

    private void checkReservationUI(Customer contact){
        Reservation res = ReservationMgr.checkReservation(contact);			        
		System.out.println("Reservation made: ");
		System.out.println(res.toString());
		return;	
    }
    private void removeReservationUI(Reservation reservation){
        ReservationMgr.removeReservation(reservation);			        
		System.out.println("Reservation removed: ");
		System.out.println(reservation.toString());
		return;	

    }
    private void printReservationUI(){
        List<Reservation> reservations = ReservationMgr.getAllReservations();
		if (reservations.isEmpty()) {
        	System.out.println("No records found!");
        }
		else {
			System.out.println("Printing all reservations in the system record...");
			for(Reservation reservation : reservations) {
				System.out.println(reservation.toString());
				System.out.print('\n');
			}
		}

    }   
}
