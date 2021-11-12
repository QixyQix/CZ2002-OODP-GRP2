package global;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Scanner;

public class CurrentTime {
    public static LocalDateTime currentTime = LocalDateTime.now();

    public static void setCurrentTime(){
        if(currentTime == null){
            currentTime = LocalDateTime.now();
        }
        Scanner sc = new Scanner(System.in);
        int option = 0,minutes;
        System.out.println("Current Time is " + currentTime.toLocalDate().toString() + " " + currentTime.getHour() + ":" + currentTime.getMinute());
        do{
            try{
                
                System.out.println("(1) - add minutes (2) - minus minutes (3)- end");
                
                do{
                    option = sc.nextInt();
                    //sc.nextLine();
                }while(option != 1 && option !=2 && option != 3);
                if(option == 3) break;

                System.out.println("Enter the minutes you want to change");
                minutes = sc.nextInt();
                //sc.nextLine();
                if(option==1) currentTime = currentTime.plusMinutes(minutes);
                else currentTime=currentTime.minusMinutes(minutes);

                System.out.println("Succesfully change! Current Time is " + currentTime.toLocalDate().toString() + " " + currentTime.getHour() + ":" + currentTime.getMinute());
            }
            catch (Exception ex) {
                System.out.println("Please enter Valid Input");
            }

            
        }while(option != 3);
        
        sc.close();
    }


        /***
     * Serializes and saves the MenuItem objects into the data/menuItems folder
     * Creates the data/menuItems folder if it does not exist
     * 
     * @throws IOException
     */
    public static void saveData() throws IOException {
        // Create directory & clear exisring data if needed
        File dataDirectory = new File("./data/currentTime");
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        } else {
            for (File existingData : dataDirectory.listFiles()) {
                existingData.delete();
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream("./data/currentTime/currentTime" );
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(currentTime);
        objectOutputStream.flush();
        objectOutputStream.close();
         
    }

    /***
     * Reads Serialized MenuItem data in the data/menuItems folder and stores it
     * into the items HashMap
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void loadSavedData() throws IOException, ClassNotFoundException {
        File dataDirectory = new File("./data/currentTime");
        File fileList[] = dataDirectory.listFiles();

        if (fileList == null)
            return;
        File file = fileList[0];

        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        
        currentTime = (LocalDateTime)objectInputStream.readObject();
        objectInputStream.close();
        
    }
}
