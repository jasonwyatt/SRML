package co.jasonwyatt.srml;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Defines a class capable of loading a {@link Bitmap} from a url. Can be registered with SRML
 * via {@link SRML#setImageLoader(SRMLImageLoader)}.
 *
 * @author jason
 */
public interface SRMLImageLoader {
    /**
     * Load the image at the given url and return it as a Bitmap.
     * @param context The current context.
     * @param url The image's url.
     * @return A bitmap of the image.
     */
    Bitmap loadImage(Context context, String url);

    /**
     * Load the image at the given url and return it as a Bitmap in the provided size..
     * @param context The current context.
     * @param url The image's url.
     * @param width Requested width of the image.
     * @param height Requested height of the image.
     * @return A bitmap.
     */
    Bitmap loadImage(Context context, String url, int width, int height);
}
