package tcg.model.exceptions;

public class QuantityExceededException extends Exception{
    
    public QuantityExceededException() {
        super("Quantity exceeds max quantity");
    }
}
