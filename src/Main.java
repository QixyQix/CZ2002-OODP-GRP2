import ui.MainUI;

import java.io.IOException;
import global.CurrentTime;

class Main{

    public static void main(String arg[]) throws ClassNotFoundException, IOException{
        CurrentTime.loadSavedData();
        MainUI.getInstance().systemBoot();  
    }  
}