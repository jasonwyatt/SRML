package co.jasonwyatt.srml.tags;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jason on 11/10/16.
 *
 * Creates {@link Tag} instances according to the supplied tag name.
 */
public final class TagFactory {
    private Map<String, Constructor<? extends Tag>> mConstructorMap = new HashMap<>();
    public TagFactory() {
        // nothing...
    }

    public Tag getTag(String tagName, String tagText, int startPosition) {
        if (Bold.NAME.equalsIgnoreCase(tagName)) {
            return new Bold(tagText, startPosition);
        }
        if (Italic.NAME.equalsIgnoreCase(tagName)) {
            return new Italic(tagText, startPosition);
        }
        if (Underline.NAME.equalsIgnoreCase(tagName)) {
            return new Underline(tagText, startPosition);
        }
        if (Strikethrough.NAME.equalsIgnoreCase(tagName)) {
            return new Strikethrough(tagText, startPosition);
        }
        if (mConstructorMap.containsKey(tagName.toLowerCase())) {
            try {
                return mConstructorMap.get(tagName.toLowerCase()).newInstance(tagText, startPosition);
            } catch (InstantiationException e) {
                throw new CouldNotCreateTagException(e, tagName);
            } catch (IllegalAccessException e) {
                throw new CouldNotCreateTagException(e, tagName);
            } catch (InvocationTargetException e) {
                throw new CouldNotCreateTagException(e, tagName);
            }
        }
        throw new BadTagException("Could not create Tag for {{" + tagText + "}} at position:" + startPosition);
    }

    public void registerTag(String tagName, Class<? extends Tag> tagClass) {
        try {
            mConstructorMap.put(tagName.toLowerCase(), tagClass.getConstructor(String.class, int.class));
        } catch (NoSuchMethodException e) {
            throw new CouldNotRegisterTagException(e, tagClass);
        }
    }
}