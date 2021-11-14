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
    private static ReservationMgr INSTANCE;
    private HashMap<Integer, Reservation> reservations;
    private int nextId=1;

    /**
     * Constructor
     */
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

    /**
     * Downcast from entities to reservationMgr
     * 
     * @param object
     */
    public void downCast(HashMap<Integer, Entities> object){
        for(int id: object.keySet()){
            if(object.get(id) instanceof Reservation)
                this.reservations.put(id,(Reservation) object.get(id));
            else throw new ClassCastException();
        }
    }

    /**
     * Upcast reservationMgr to entities in a hashmap
     * 
     * @return Hashmap object
     */
    public HashMap<Integer, Entities> upCast(){
        HashMap<Integer, Entities> object = new HashMap<Integer, Entities>();
        for(int id: reservations.keySet()){
           object.put(id,reservations.get(id)); 
        }
        return object;
    }
    
    /***
     * Save data
     * 
     * @throws IOException if stream to file cannot be written to or closed
     */
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
        if (INSTANCE == null) {
            INSTANCE = new ReservationMgr();
        }
        return INSTANCE;
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
    public Reservation createReservation(Customer customer, LocalDateTime date, int noOfPax, Table table) {
        Reservation reservation = new Reservation(customer, date, noOfPax, table, this.nextId);
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
    public Reservation checkReservation(String contact) {
        Reservation result = null;
        LocalDateTime current = LocalDateTime.now();
        ArrayList<Integer> IDsToRemove = new ArrayList<Integer>();
        for (Reservation reservation : reservations.values()) {
            // remove expired reservations
            LocalDateTime expiredTime = reservation.getCheckInTime().plusMinutes(15);
            if (current.isAfter(expiredTime) == true && reservation.getCheckInStatus() == false) {
                IDsToRemove.add(reservation.getId());
            } 
            else if (reservation.getCustomer().getContact() == contact){
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
    public void removeReservation(Reservation reservation) {
        this.reservations.remove(reservation.getId());
        TableMgr.getInstance().deallocateTable(reservation.getTable(), reservation.getCheckInTime());
        this.reservations.remove(reservation.getId());
    }

    /**
     * Returns hashmap of reservation id and Reservation object
     * 
     * @return HashMaps of all reservation id and Reservation object
     */
    public HashMap<Integer, Reservation> getAllReservations() {
        LocalDateTime current = LocalDateTime.now();
        System.out.println("Current Time now is " + current.toString() );
        ArrayList<Integer> IDsToRemove = new ArrayList<Integer>();
        for (Reservation reservation : reservations.values()) {
            // remove expired reservations
            LocalDateTime expiredTime = reservation.getCheckInTime().plusMinutes(15);
            if (current.isAfter(expiredTime) == true && reservation.getCheckInStatus() == false) {
                IDsToRemove.add(reservation.getId());
                System.out.println("Reservation ID:  " + reservation.getId()+ " has been removed because it has passed 15mins from the supposed Check In Time of "+ reservation.getCheckInTime().toString());
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
