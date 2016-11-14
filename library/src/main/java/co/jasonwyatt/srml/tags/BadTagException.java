package co.jasonwyatt.srml.tags;

/**
 * @author jason
 *
 * Thrown when we could not find a tag class for the given tag text.
 */
public class BadTagException extends RuntimeException {
    public BadTagException(String message) {
        super(message);
    }
}
