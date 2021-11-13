package ui;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import entities.Staff;

/***
 * Represents a UserInterface Template for UI to get User Input
 * 
 *  @author Cho Qi Xiang
 * @version 1.0
 * @since 2021-11-14
 */
public class UserInterface {
    /**
     *  Scanner to getting User Input
     */
    private Scanner sc;
    /**
     *  Staff that are using the system
     */
    private static Staff staff;

    /**
     *  Get User Input to check in the Reservation  
     * 
     */
    public UserInterface() {
        sc = new Scanner(System.in);
    }

    /**
     *  Get String Input
     *@return Input String
     */
    private String getInputString(){
        String input;
        while(true){
            input = sc.nextLine().trim();
            if(!input.isEmpty()) return input;
        }
    }

    /**
     *  Get String Input with prompt
     *@param prompt Printing prompt
     *@return Input String
     */
    public String getInputString(String prompt) {
        System.out.println(prompt);
        return getInputString();
    }

    /**
     *  Get Dummy Input with prompt
     *@param prompt Printing prompt
     *@return Input String
     */
    public String getDummy(String prompt){
        System.out.println(prompt);
        return sc.nextLine().trim(); 
    }


    /**
     *  Get Dummy Input with prompt
     *@param prompt Printing prompt
     *
     */
    public void waitEnter(){
        getDummy("Press Enter to continue ... ");
        return;
    }

    /**
     *  Get Integer Input with prompt
     *@param prompt Printing prompt
     *@return Input Integer
     */
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

    /**
     *  Get Integer Input within range with prompt
     * @param prompt Printing prompt
     * @param min    mininum value of the input
     * @param max    maximum value of the input 
     * @return Input Integer
     */
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

    /**
     *  Get Double Input with prompt
     *@param prompt Printing prompt
     *@return Input Double
     */
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

    /**
     *  Get Double Input within range with prompt
     * @param prompt Printing prompt
     * @param min    mininum value of the input
     * @param max    maximum value of the input 
     * @return Input Double
     */
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

    /**
     *  Get Date & Time Input with prompt
     * @param prompt Printing prompt
     * @return Input Date Time
     */
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

    /**
     *  Get Date Input with prompt
     * @param prompt Printing prompt
     * @return Input Date
     */
    public LocalDate getInputDate(String prompt){
        while(true){
            try{
                System.out.println(prompt);
                String input = this.getInputString().replace(" ", "");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//input format:yyyy-mm-ddThh:mm	
                LocalDate input_date = LocalDate.parse(input, formatter);
                
                return input_date;
            } catch(Exception ex){
                System.out.println("Please enter a valid Date");
                
            }
        }
        
    }

    /**
     *  Get True/False Option from User with prompt
     * @param prompt Printing prompt
     * @return boolean 
     */
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

    /**
     *  Get Contact from User with prompt
     * @param prompt Printing prompt
     * @return Contact Number (Singapore Number) of the User 
     */
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

    /**
     *  Get Gender from User with prompt
     * @param prompt Printing prompt
     * @return String of Male or Female 
     */
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
    
    /**
     *  Getter of the Staff
     * @return Staff 
     */
    public static Staff getStaff(){
        return staff;
    }
    
    /**
     *  Setter of the Staff
     * 
     */
    public static void setStaff(Staff s){
        staff = s;
    }

}
