package ui;

import managers.TableMgr;

import java.time.LocalDateTime;

public class TableUI extends UserInterface{
    private static TableUI instance;
    private TableUI() {};

    /**
     * Returns the TableMgr instance and creates an instance if it does not exist
     * 
     * @return instance
     */
    public static TableUI getInstance() {
        if (instance == null) {
            instance = new TableUI();
        }
        return instance;
    }

    public void displayOptions() {
        System.out.println("====Table Manager====");
        System.out.println("(1) Check table availability");
        System.out.println("(2) Create tables");
        System.out.println("(-1) Exit");
    }

    public void selectOption() {
        int option = 0;
        int noOfPax;
        LocalDateTime date;
        do {
            displayOptions();
            option = super.getInputInt("Please enter your choice: ");

            switch (option) {
            case 1:
                noOfPax = super.getInputInt("How many pax?: ");
                date = super.getInputDateTime("Which date and time? (yyyy-MM-dd HH:mm): ");
             
                TableMgr.getInstance().checkTableAvailability(noOfPax, date);
                break;
            case 2:
                int idCounter = 0;
                for (int j = 2; j <= 10; j = j + 2) {
                    int noOfTablePax = getInputInt("How many tables of " + j + " pax: ");
                    for (int i = 0; i < noOfTablePax; i++) {
                        TableMgr.getInstance().createTable(noOfTablePax, ++idCounter);
                    }
                }
                break;
              
            }
        } while (option != -1);
    }
}
