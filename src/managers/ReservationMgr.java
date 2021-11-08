package managers;

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import entities.Reservation;
import entities.Customer;
import entities.Table;

public final class ReservationMgr{
    private static ReservationMgr instance;
    private HashMap<Integer, Reservation> reservations;
    private int reservationID;

    /**
     * Constructor
     */
    private ReservationMgr(){
        this.reservations = new HashMap<Integer, Reservation>();
        try {
            loadSavedData();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Failed to load reservation data");
        }
    };

    /**
     * Returns the ReservationMgr instance and creates an instance if it does not exist
     * 
     * @return
     */
    public static ReservationMgr getInstance(){
        if(instance == null){
            instance = new ReservationMgr();
        }
        return instance;
    }

    public Reservation createReservation(Customer customer, LocalDateTime checkInDateTime, int noOfPax, Table table){
        Reservation reservation = new Reservation(customer,checkInDateTime,noOfPax,table, this.reservationID);
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
        TableMgr tableMgr = TableMgr.getInstance();
        tableMgr.deallocateTable(reservationMade.getTable(), reservationMade.getCheckInTime());
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

        /***
     * Serializes and saves the reservation objects into the data/reservation folder
     * Creates the data/reservation folder if it does not exist
     * 
     * @throws IOException
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
    }
        /***
     * Reads Serialized Reservation data in the data/reservation folder and stores it
     * into the reservations HashMap
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadSavedData() throws IOException, ClassNotFoundException {
        File dataDirectory = new File("./data/menuItems");
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return;

        for (File file : fileList) {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Reservation reservation = ReservationMgr.getInstance()
                    .createReservationFromSerializedData(objectInputStream.readObject());
            this.reservations.put(reservation.getreservationID(), reservation);
            objectInputStream.close();
        }
    }

    public Reservation createReservationFromSerializedData(Object o) throws ClassNotFoundException{
        if(o instanceof Reservation){
            return (Reservation) o;
        }else{
            throw new ClassNotFoundException();
        }
    }

}
