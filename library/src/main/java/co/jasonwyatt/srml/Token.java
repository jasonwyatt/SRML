package co.jasonwyatt.srml;

import java.util.regex.Pattern;

/**
 * Created by jason on 10/31/16.
 */

public enum Token {
    OPEN_START_TAG("\\{\\{"),
    OPEN_END_TAG("\\{\\{\\/"),
    CLOSE_TAG("\\}\\}"),

    TAG_TYPE("bold|italic|underline|strike|color|link"),
    SPACE("\\s+"),
    ;

    private final Pattern pattern;
    Token(String pattern) {
        this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
    }
}
