package tcg.model.exceptions;

public class UserNotFoundException extends Exception{
    
    public UserNotFoundException(String username) {
        super("The user " + username + " does not exist or the password was wrong");
    }
}
