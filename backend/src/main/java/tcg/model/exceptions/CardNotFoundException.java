package tcg.model.exceptions;

public class CardNotFoundException extends Exception{
    
    public CardNotFoundException() {
        super("Card was not found");
    }
}
