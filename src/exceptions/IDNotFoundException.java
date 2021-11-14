package exceptions;

/***
 * Represents a id not found exception
 * 
 * @author Cho Qi Xiang
 * @version 1.0
 * @since 2021-11-14
 */
public class IDNotFoundException extends Exception {
    /**
     * Sets exception message
     * 
     */
    public IDNotFoundException() {
        super("The provided ID does not exist");
    }

    /**
     * Prints exception message
     * 
     * @param message exception message
     */
    public IDNotFoundException(String message) {
        super(message);
    }
}
