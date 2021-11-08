package ui;

import java.lang.invoke.TypeDescriptor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserInterface {
    private Scanner sc;

    public UserInterface() {
        sc = new Scanner(System.in);
    }

    public String getInputString(String prompt) {
        System.out.println(prompt);
        String input = sc.nextLine();
        return input.trim();
    }

    public int getInputInt(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                int input = sc.nextInt();
                sc.nextLine();

                return input;
            } catch (Exception ex) {
                System.out.println("Please enter a valid int");
                sc.nextLine();
            }
        }
    }

    public int getInputInt(String prompt, int min, int max) {
        while (true) {
            int input = getInputInt(prompt);
            if (input > max || input < min) {
                System.out.println("Please enter a value between " + min + " and " + max);
            } else {
                return input;
            }
        }
    }

    public double getInputDouble(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                double input = sc.nextDouble();
                sc.nextLine();

                return input;
            } catch (Exception ex) {
                System.out.println("Please enter a valid double");
                sc.nextLine();
            }
        }
    }

    public double getInputDouble(String prompt, double min, double max) {
        while (true) {
            double input = getInputDouble(prompt);
            if (input > max || input < min) {
                System.out.println("Please enter a value between " + min + " and " + max);
            } else {
                return input;
            }
        }
    }

    public LocalDateTime getInputDateTime(String prompt){
        while(true){
            try{
                System.out.println(prompt);
                String input = sc.nextLine().trim().replace(" ", "T");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");//input format:yyyy-mm-ddThh:mm	
                LocalDateTime input_date = LocalDateTime.parse(input, formatter);
                sc.nextLine();
                return input_date;
            } catch(Exception ex){
                System.out.println("Please enter a valid Date");
                sc.nextLine();
            }
        }
        
    }

    public LocalDate getInputDate(String prompt){
        while(true){
            try{
                System.out.println(prompt);
                String input = sc.nextLine().trim().replace(" ", "T");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//input format:yyyy-mm-ddThh:mm	
                LocalDate input_date = LocalDate.parse(input, formatter);
                sc.nextLine();
                return input_date;
            } catch(Exception ex){
                System.out.println("Please enter a valid Date");
                sc.nextLine();
            }
        }
        
    }

    public boolean getYNOption(String prompt){
        while(true){
            try{
                System.out.println(prompt);
                char choice = sc.next().charAt(0);
                sc.nextLine();
                if(choice =='Y'||choice == 'y') return true;
                else if (choice =='N' || choice == 'n') return false;
                else {
                    System.out.println("Please enter valid option, Y or N");
                }
            }catch(Exception ex){
                System.out.println("Please enter valid option, Y or N");
                

            }
        }

    }

    // TODO ask for contact?

}
