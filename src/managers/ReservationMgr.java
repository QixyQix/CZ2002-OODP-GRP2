package managers;

import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import entities.Reservation;
import entities.Customer;
import entities.Table;

public class ReservationMgr {
    private static ReservationMgr instance = null;
    private  ArrayList<Reservation> reservationList = new ArrayList<>();

    private ReservationMgr(){};

    public static ReservationMgr getInstance(){
        if(instance == null){
            instance = new Reservation();
        }
        return instance;
    }

    public static Reservation createReservation(Customer customer, String checkInTime, int noOfPax, Table table ) {
        // Customer customer;
        // LocalDateTime date;
        // int noOfPax;
        // Table table;
        // boolean valid;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");//input format:yyyy-mm-ddThh:mm	
    	LocalDateTime checkInTime = LocalDateTime.parse(checkInTime, formatter);
        Reservation reservation = new Reservation()

        // do{
        //     System.out.println("Enter Reserved Date (dd/mm/yyyy)");

        // }
    }
    public static Reservation checkReservation(String contact) {}
    public static void removeReservation(Reservation reservation) {}
    public static Reservation [] getAllReservations() {}
    
}
