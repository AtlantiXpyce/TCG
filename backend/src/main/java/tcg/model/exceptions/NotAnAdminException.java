package tcg.model.exceptions;

public class NotAnAdminException extends Exception{
    
    public NotAnAdminException() {
        super("You are not an admin");
    }
}
