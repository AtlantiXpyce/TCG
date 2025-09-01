package tcg.model.exceptions;

public class CartNotFoundException extends Exception{
    
    public CartNotFoundException() {
        super("The cart was not found");
    }
}
