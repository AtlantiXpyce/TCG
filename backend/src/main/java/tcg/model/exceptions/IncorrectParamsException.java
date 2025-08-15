package tcg.model.exceptions;

public class IncorrectParamsException extends Exception{
    
    public IncorrectParamsException() {
        super("All params must be present and have a valid value");
    }
}
