package tcg.model.exceptions;

public class AlreadyExistsException extends Exception{
    
    public AlreadyExistsException(String username) {
        super("The user" + username + "already exists");
    }
}
