package tcg.model.exceptions;

public class UserNotFoundException extends Exception{
    
    public UserNotFoundException() {
        super("The user may not exist");
    }
}
