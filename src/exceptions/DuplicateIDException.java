package exceptions;

public class DuplicateIDException extends Exception{
    public DuplicateIDException(){
        super("The provided ID is already taken");
    }

    public DuplicateIDException(String message){
        super(message);
    }
}
