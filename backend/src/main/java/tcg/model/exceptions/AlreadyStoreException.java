package tcg.model.exceptions;

public class AlreadyStoreException extends Exception{
    public AlreadyStoreException() {
        super("The user is already a store");
    }
}
