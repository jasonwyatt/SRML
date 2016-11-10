package co.jasonwyatt.srml.tags;

/**
 * Created by jason on 11/10/16.
 *
 * Thrown when we could not find a tag class for the given tag text.
 */
public class BadTagException extends RuntimeException {
    public BadTagException(String message) {
        super(message);
    }
}
