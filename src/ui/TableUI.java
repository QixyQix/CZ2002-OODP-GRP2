package ui;

import managers.TableMgr;

import java.time.LocalDateTime;

public class TableUI extends UserInterface {
    private static TableUI INSTANCE;

    private TableUI() {
    };

    /**
     * Returns the TableMgr instance and creates an instance if it does not exist
     * 
     * @return instance
     */
    public static TableUI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TableUI();
        }
        return INSTANCE;
    }

    private void displayOptions() {
        System.out.println("==========Table Manager==========");
        System.out.println("(0) Go Back to Main Page Exit");
        System.out.println("(1) Check table availability");
        System.out.println("(2) Create tables");
        System.out.println("(3) View all tables");
        System.out.println("=================================");
    }

    public void showSelection() {
        int option = 0;
        
        do {
            displayOptions();
            option = super.getInputInt("Please enter your choice: ");

            switch (option) {
            case 1:
                checkTableAvailblility();
                break;
            case 2:
                createTable();
                break;
            case 3:
                TableMgr.getInstance().printAllTables();
                break;
            }
            super.waitEnter();
        } while (option != 0);
    }

    private void checkTableAvailblility(){
        int noOfPax;
        LocalDateTime date;
        noOfPax = super.getInputInt("How many pax?: ");
        date = super.getInputDateTime("Which date and time? (yyyy-MM-dd HH:mm): ");
                
        TableMgr.getInstance().printTableAvailability(noOfPax, date);
    }
    
    private void createTable(){
        
        if( ! super.getYNOption("Do you sure you want to overide the current Tables Object?")) 
            return;

        int idCounter = 0;
        for (int j = 2; j <= 10; j = j + 2) {
            int noOfTableOfJPax = getInputInt("How many tables of " + j + " pax: ");
            for (int i = 0; i < noOfTableOfJPax; i++) {
                TableMgr.getInstance().createTable(j, ++idCounter);
            }
        }
    }

    
}
