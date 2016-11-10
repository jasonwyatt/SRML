package co.jasonwyatt.srml.tags;

/**
 * Created by jason on 11/10/16.
 *
 * Thrown when we could not register a user-supplied Tag class.
 */
public class CouldNotRegisterTagException extends RuntimeException {
    CouldNotRegisterTagException(Throwable e, Class<? extends Tag> tagClass) {
        super("Could not register "+tagClass.getName(), e);
    }
}
