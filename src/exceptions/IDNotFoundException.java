package exceptions;

public class IDNotFoundException extends Exception{
    public IDNotFoundException(){
        super("The provided ID does not exist");
    }

    public IDNotFoundException(String message){
        super(message);
    }
}
