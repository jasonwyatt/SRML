package co.jasonwyatt.srml.tags;

/**
 * Created by jason on 11/10/16.
 * Thrown by the {@link TagFactory} when a tag could not be instantiated.
 */
public class CouldNotCreateTagException extends RuntimeException {
    CouldNotCreateTagException(Exception e, String tagName) {
        super("Error instantiating Tag with name: "+tagName, e);
    }
}
