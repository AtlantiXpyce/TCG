package tcg.model.exceptions;

public class CardStockNotFoundException extends Exception{
    
    public CardStockNotFoundException() {
        super("CardStock was not found");
    }
}
