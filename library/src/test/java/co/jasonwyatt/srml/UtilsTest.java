package co.jasonwyatt.srml;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class UtilsTest {
    @Test
    public void join_simple() {
        assertEquals("foo", Utils.join("|", new String[]{"foo"}));
        assertEquals("foo|bar", Utils.join("|", new String[]{"foo", "bar"}));
        assertEquals("foo|bar|baz", Utils.join("|", new String[]{"foo", "bar", "baz"}));
    }

    @Test
    public void join_edge() {
        assertEquals("", Utils.join("|", new String[]{}));
    }
}
