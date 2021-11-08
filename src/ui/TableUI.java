package ui;

import managers.TableMgr;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class TableUI {
    private static TableUI instance;
    private static Scanner sc = new Scanner(System.in);

    private TableUI() {
    };

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
        System.out.println("(-1) Exit");
    }

    public void selectOption() {
        int option = 0;
        int noOfPax;
        LocalDateTime date;
        do {
            displayOptions();
            try {
                option = sc.nextInt();
                sc.nextLine();
            } catch (Exception ex) {
                System.out.println("Invalid input");
                continue;
            }

            switch (option) {
            case 1:
                while (true) {
                    try {
                        System.out.println("How many pax?: ");
                        noOfPax = sc.nextInt();
                        sc.nextLine();
                        break;
                    } catch (Exception ex) {
                        System.out.println("Invalid input");
                        continue;
                    }
                }
                while (true) {
                    try {
                        System.out.println("Which date and time? (yyyy-MM-dd HH:mm): ");
                        String dateString = sc.nextLine().trim().replace(" ", "T");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                        date = LocalDateTime.parse(dateString, formatter);
                        sc.nextLine();
                        break;
                    } catch (Exception ex) {
                        System.out.println("Invalid input");
                        continue;
                    }
                }
                checkTableAvailability(noOfPax, date);
                break;
            }
        } while (option != -1);
    }

    private boolean checkTableAvailability(int noOfPax, LocalDateTime date) {
        TableMgr tablemgr = TableMgr.getInstance();
        return tablemgr.checkTableAvailability(noOfPax, date);
    }
}
