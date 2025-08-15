package tcg.model.exceptions;

public class NotTheUserException extends Exception{
    
    public NotTheUserException() {
        super("You are not that user");
    }
}
