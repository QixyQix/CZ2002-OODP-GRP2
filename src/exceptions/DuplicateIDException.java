package exceptions;

/***
 * Represents a discount filter enum
 * 
 * @author Cho Qi Xiang
 * @version 1.0
 * @since 2021-11-14
 */
public class DuplicateIDException extends Exception {
    /**
     * Sets exception message
     * 
     */
    public DuplicateIDException() {
        super("The provided ID is already taken");
    }

    /**
     * Prints exception message
     * 
     * @param message exception message
     */
    public DuplicateIDException(String message) {
        super(message);
    }
}
