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
        System.out.println("(2) Create tables");
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
                        System.out.println("Which date and time? (yyyy-MM-dd HH:mm): ");
                        String dateString = sc.nextLine().trim().replace(" ", "T");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                        date = LocalDateTime.parse(dateString, formatter);
                        sc.nextLine();
                        TableMgr.getInstance().checkTableAvailability(noOfPax, date);
                        break;
                    } catch (Exception ex) {
                        System.out.println("Invalid input");
                        continue;
                    }
                }
                break;
            case 2:
                while (true) {
                    try {
                        int idCounter = 0;
                        for (int j = 0; j < 11; j = j + 2) {
                            System.out.println("How many tables of " + j + " pax: ");
                            int noOfTablePax = sc.nextInt();
                            for (int i = 0; i < noOfTablePax; i++) {
                                TableMgr.getInstance().createTable(noOfTablePax, idCounter++);
                            }
                        }
                        break;
                    } catch (Exception ex) {
                        System.out.println("Invalid input");
                        continue;
                    }
                }
                break;
            }
        } while (option != -1);
    }
}
