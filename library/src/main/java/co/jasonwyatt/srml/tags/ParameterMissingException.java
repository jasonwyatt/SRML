package co.jasonwyatt.srml.tags;

/**
 * Created by jason on 11/14/16.
 *
 * This exception can be thrown by a {@link ParameterizedTag} when a required parameter is missing.
 */
public class ParameterMissingException extends RuntimeException {
    public ParameterMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}
