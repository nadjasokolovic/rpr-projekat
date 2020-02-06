package ba.unsa.etf.rpr.projekat;

public class NonExistentUserException extends Exception {
    public NonExistentUserException(String message) {
        super(message);
    }
}
