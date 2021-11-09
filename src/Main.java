import ui.MainUI;

import java.io.IOException;

import entities.Membership;
import global.CurrentTime;
import entities.*;
import managers.*;
import enums.*;
import factories.*;
import exceptions.*;
import ui.*;

class Main{

    public static void main(String arg[]) throws ClassNotFoundException, IOException{
        // CurrentTime.loadSavedData();
         MainUI.getInstance().systemBoot();  
        //Membership m = new Membership();
        StaffUI.getInstance();
    }  
}