package managers;

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;

import entities.Reservation;
import entities.Customer;
import entities.Table;

public final class ReservationMgr {
    private static ReservationMgr instance;
    private HashMap<Integer, Reservation> reservations;
    private int reservationID;

    private ReservationMgr() {
        this.reservations = new HashMap<Integer, Reservation>();
        try {
            loadSavedData();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load reservation data");
        }
    };

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
     * @param customer Customer object
     * @param date     date and time
     * @param noOfPax  number of pax at the table
     * @param table    Table object
     * @return Reservation object
     */
    public Reservation createReservation(Customer customer, LocalDateTime checkInDateTime, int noOfPax, Table table) {
        Reservation reservation = new Reservation(customer, checkInDateTime, noOfPax, table, this.reservationID);
        reservations.put(this.reservationID, reservation);
        this.reservationID += 1;
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
        for (Reservation reservation : reservations.values()) {
            // remove expired reservations
            LocalDateTime expiredTime = reservation.getCheckInTime().plusMinutes(15);
            if (current.isAfter(expiredTime) == true) {
                removeReservation(reservation);
            } else if (reservation.getCustomer().getContact() == contact)
                result = reservation;
        }
        return result;
    }

    /**
     * Removes a reservation
     * 
     * @param reservationMade
     */
    public void removeReservation(Reservation reservationMade) {
        this.reservations.remove(reservationMade.getreservationID());
        TableMgr.getInstance().deallocateTable(reservationMade.getTable(), reservationMade.getCheckInTime());
    }

    /**
     * Returns hashmap of reservation id and Reservation object
     * 
     * @return HashMaps of all reservation id and Reservation object
     */
    public HashMap<Integer, Reservation> getAllReservations() {
        LocalDateTime current = LocalDateTime.now();
        for (Reservation reservation : reservations.values()) {
            // remove expired reservations
            LocalDateTime expiredTime = reservation.getCheckInTime().plusMinutes(15);
            if (current.isAfter(expiredTime) == true) {
                removeReservation(reservation);
            }
        }
        return reservations;
    }

    /***
     * Serializes and saves the reservation objects into the data/reservation folder
     * Creates the data/reservation folder if it does not exist
     * 
     * @throws IOException if stream to file cannot be written to or closed
     */
    public void saveData() throws IOException {
        // Create directory & clear exisring data if needed
        File dataDirectory = new File("./data/reservations");
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        } else {
            for (File existingData : dataDirectory.listFiles()) {
                existingData.delete();
            }
        }

        for (int reservationID : reservations.keySet()) {
            Reservation reservation = reservations.get(reservationID);

            FileOutputStream fileOutputStream = new FileOutputStream("./data/reservations/" + reservationID);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(reservation);
            objectOutputStream.flush();
            objectOutputStream.close();
        }
        FileOutputStream fileOutputStream = new FileOutputStream("./data/reservationID");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeInt(reservationID);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    /***
     * Reads Serialized Reservation data in the data/reservation folder and stores
     * it into the reservations HashMap
     * 
     * @throws IOException            if stream to file cannot be written to or
     *                                closed
     * @throws ClassNotFoundException if serialized data is not of the Customer
     *                                class
     */
    public void loadSavedData() throws IOException, ClassNotFoundException {
        File dataDirectory = new File("./data/reservations");
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return;

        for (File file : fileList) {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            Reservation reservation = (Reservation) objectInputStream.readObject();
            this.reservations.put(reservation.getreservationID(), reservation);
            objectInputStream.close();
        }
        try {
            FileInputStream fileInputStream = new FileInputStream("reservationID");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            this.reservationID = (int) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            this.reservationID = 1;
        }
    }

}
