package entities;

import java.time.LocalDateTime;

public class Reservation {
    private LocalDateTime date;
    private int noOfPax;
    private Customer customer;
    private Table table;
    private boolean valid;

}
