package co.jasonwyatt.srml.tags;

/**
 * Created by jason on 11/14/16.
 *
 * This exception can be thrown by a {@link ParameterizedTag} when a parameter is invalid for some reason.
 */
public class BadParameterException extends RuntimeException {
    public BadParameterException(String message) {
        super(message);
    }

    public BadParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
