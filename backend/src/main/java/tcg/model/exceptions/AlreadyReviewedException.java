package tcg.model.exceptions;

public class AlreadyReviewedException extends Exception{
    
    public AlreadyReviewedException() {
        super("The order was already reviewed");
    }
}
