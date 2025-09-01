package tcg.model.exceptions;

public class EmptyCartException extends Exception{
    
    public EmptyCartException() {
        super("Cart is empty");
    }
}
