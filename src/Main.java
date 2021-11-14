import ui.MainUI;

import java.io.IOException;

/***
 * Represents a MainApplication
 * 
 * @author Lee Zong Yu
 * @version 1.0
 * @since 2021-11-14
 */
class Main{
    
    /**
     * Main Application
     * @param arg arguments
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static void main(String arg[]) throws ClassNotFoundException, IOException{

        MainUI.getInstance().systemBoot();  
    }  
}