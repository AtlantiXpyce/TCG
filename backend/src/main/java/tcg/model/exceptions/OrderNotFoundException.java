package tcg.model.exceptions;

public class OrderNotFoundException extends Exception{
    
    public OrderNotFoundException() {
        super("Order was not found");
    }
}
