package managers;

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;

import entities.Reservation;
import entities.Customer;
import entities.Entities;
import entities.Table;

/***
 * Represents a reservation manager
 * 
 * @author Eang Sokunthea
 */
public final class ReservationMgr extends DataMgr {
    private static ReservationMgr instance;
    private HashMap<Integer, Reservation> reservations;
    private int nextId;

    private ReservationMgr() {
        this.reservations = new HashMap<Integer, Reservation>();
        try {
            this.downCast(super.loadSavedData("reservations"));
            nextId = super.loadNextIdData("reservationNextId");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load reservation data");
        }
    };

    public void downCast(HashMap<Integer, Entities> object){
        for(int id: object.keySet()){
            if(object.get(id) instanceof Reservation)
                this.reservations.put(id,(Reservation) object.get(id));
            else throw new ClassCastException();
        }
    }

    public HashMap<Integer, Entities> upCast(){
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for(int id: reservations.keySet()){
           object.put(id,reservations.get(id)); 
        }
        return object;
    }
    
    public void saveData() throws IOException {
        saveDataSerialize(upCast(), nextId, "reservations", "reservationNextId");
    }

    /**
     * Returns the ReservationMgr instance and creates an instance if it does not
     * exist
     * 
     * @return instance
     */
    public static ReservationMgr getInstance() {
        if (instance == null) {
            instance = new ReservationMgr();
        }
        return instance;
    }

    /**
     * Creates and returns Reservation object
     * 
     * @param customer        Customer object
     * @param checkInDateTime date and time
     * @param noOfPax         number of pax at the table
     * @param table           Table object
     * @return Reservation object
     */
    public Reservation createReservation(Customer customer, LocalDateTime checkInDateTime, int noOfPax, Table table) {
        Reservation reservation = new Reservation(customer, checkInDateTime, noOfPax, table, this.nextId);
        reservations.put(this.nextId, reservation);
        this.nextId += 1;
        return reservation;

    }

    /**
     * Returns Reservation object or null
     * 
     * @param contact customer contact number
     * @return Reservation object if Reservation object corresponding to customer
     *         contact number exists, null if it does not exist
     */
    public Reservation checkReservation(Customer customer) {
        Reservation result = null;
        LocalDateTime current = LocalDateTime.now();
        ArrayList<Integer> IDsToRemove = new ArrayList<Integer>();
        for (Reservation reservation : reservations.values()) {
            // remove expired reservations
            LocalDateTime expiredTime = reservation.getCheckInTime().plusMinutes(15);
            if (current.isAfter(expiredTime) == true && reservation.getCheckInStatus() == false) {
                IDsToRemove.add(reservation.getId());
            } 
            else if (reservation.getCustomer().getContact() == customer.getContact()){
                result = reservation;
            }
        }
        int counter=0;
        while(IDsToRemove.size()>counter){
            Reservation resToRemoved = this.reservations.get(IDsToRemove.get(counter));
            removeReservation(resToRemoved);
            counter++;
        }
        return result;
    }

    /**
     * 
     * Removes a reservation
     * 
     * @param reservationMade Reservation object
     */
    public void removeReservation(Reservation reservationMade) {
        this.reservations.remove(reservationMade.getId());
        TableMgr.getInstance().deallocateTable(reservationMade.getTable(), reservationMade.getCheckInTime());
        this.reservations.remove(reservationMade.getId());
    }

    /**
     * Returns hashmap of reservation id and Reservation object
     * 
     * @return HashMaps of all reservation id and Reservation object
     */
    public HashMap<Integer, Reservation> getAllReservations() {
        LocalDateTime current = LocalDateTime.now();
        System.out.println("Current Time now is= " + current);
        ArrayList<Integer> IDsToRemove = new ArrayList<Integer>();
        for (Reservation reservation : reservations.values()) {
            // remove expired reservations
            LocalDateTime expiredTime = reservation.getCheckInTime().plusMinutes(15);
            if (current.isAfter(expiredTime) == true && reservation.getCheckInStatus() == false) {
                IDsToRemove.add(reservation.getId());
                System.out.println("Reservation ID:  " + reservation.getId()+ " has been removed because it has passed 15mins from the supposed Check In Time of "+ reservation.getCheckInTime());
            }
        }
        int counter=0;
        while(IDsToRemove.size()>counter){
            Reservation resToRemoved = this.reservations.get(IDsToRemove.get(counter));
            removeReservation(resToRemoved);
            counter++;
        }
        return reservations;
    }

}
