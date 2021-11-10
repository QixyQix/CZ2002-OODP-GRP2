package ui;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserInterface {
    private Scanner sc;

    public UserInterface() {
        sc = new Scanner(System.in);
    }
    private String getInputString(){
        String input;
        while(true){
            input = sc.nextLine().trim();
            if(!input.isEmpty()) return input;
        }
    }

    public String getInputString(String prompt) {
        System.out.println(prompt);
        return getInputString();
    }

    public String getDummy(String prompt){
        System.out.println(prompt);
        return sc.nextLine().trim(); 
    }

    public String getDummy(){
        return sc.nextLine().trim();
    }
    
    public void waitEnter(){
        getDummy("Press Enter to continue ... ");
        return;
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
                String input = this.getInputString().trim().replace(" ", "T");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");//input format:yyyy-mm-ddThh:mm	
                LocalDateTime input_date = LocalDateTime.parse(input, formatter);
                
                return input_date;
            } catch(Exception ex){
                System.out.println("Please enter a valid Date");
                
            }
        }
        
    }

    public LocalDate getInputDate(String prompt){
        while(true){
            try{
                System.out.println(prompt);
                String input = this.getInputString().replace(" ", "T");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//input format:yyyy-mm-ddThh:mm	
                LocalDate input_date = LocalDate.parse(input, formatter);
                
                return input_date;
            } catch(Exception ex){
                System.out.println("Please enter a valid Date");
                
            }
        }
        
    }

    public boolean getYNOption(String prompt){
        while(true){
            try{
                System.out.println(prompt + " (Enter 'Y' for Yes, 'N' for N) ");
                char choice = this.getInputString().charAt(0);
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

    public String getContact(String prompt){
        System.out.println(prompt);
        while(true){
            try{   
                String input = this.getInputString().replace(" ", "").replace("-","").replace("+","");
                if(input.length()==10 && input.charAt(0)=='6' && input.charAt(1)=='5' ){
                    input = input.substring(2,10);
                }
                if(input.matches("[0-9]+") && input.length()==8 )
                    return input;

                System.out.println("Please enter valid Phone Number (Singapore Number)");
            }catch(Exception ex){
                System.out.println("Please enter valid Phone Number (Singapore Number)");
            }
        }
    }

    public String getGender(String prompt){
        String input;
        System.out.println(prompt);
        while (true){
            input = this.getInputString().toLowerCase();
            
            if (input.equalsIgnoreCase("male") ) return "Male";
            else if (input.equalsIgnoreCase("female")) return "Female";

            System.out.println("Please enter Valid Gender (Male or Female) ");
        }
        
    }

    public void print(String prompt){
        System.out.println(prompt);
    }
    

}
