package managers;

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import entities.Reservation;
import entities.Customer;
import entities.Table;

public class ReservationMgr implements Serializable{
    private static ReservationMgr instance = null;
    private  HashMap<Integer, Reservation> reservations;
    private int reservationID;

    private ReservationMgr(){};

    public static ReservationMgr getInstance(){
        if(instance == null){
            instance = new ReservationMgr();
        }
        return instance;
    }

    public Reservation createReservation(Customer customer, LocalDateTime checkInDateTime, int noOfPax, Table table ) {
        Reservation reservation = new Reservation(customer,checkInDateTime,noOfPax,table,false, this.reservationID);
        reservations.put(this.reservationID, reservation);
        this.reservationID +=1;
        return reservation;

    }
    public Reservation checkReservation(String contact) {
        Reservation result = new Reservation();
        LocalDateTime current = LocalDateTime.now();
        for (Reservation reservation: reservations.values()){
            LocalDateTime expiredTime = reservation.getCheckInTime().plusMinutes(15);
            if (current.isAfter(expiredTime) == true){
                removeReservation(reservation);
            }
            //depends on CustomerMgr
            if (reservation.getCustomer().getContact() == contact)
                result = reservation;
        }
        return result;
    }

    public void removeReservation(Reservation reservationMade) {
        this.reservations.remove(reservationMade.getreservationID());
    }

    public HashMap<Integer, Reservation> getAllReservations() {
        //check if expired 
        LocalDateTime current = LocalDateTime.now();
        for (Reservation reservation: reservations.values()){
            LocalDateTime expiredTime = reservation.getCheckInTime().plusMinutes(15);
            if (current.isAfter(expiredTime) == true){
                removeReservation(reservation);
            }
        }
        return reservations;
    }
    
}
