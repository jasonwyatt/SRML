package co.jasonwyatt.srml.utils;

import org.junit.Test;

import co.jasonwyatt.srml.tags.BadParameterException;
import co.jasonwyatt.srml.utils.Utils;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

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

    @Test
    public void getColorInt_with_black_3() throws Exception {
        assertEquals(0xFF000000, Utils.getColorInt("#000"));
    }

    @Test
    public void getColorInt_with_length_3() throws Exception {
        assertEquals(0xFFFFFFFF, Utils.getColorInt("#FFF"));
        assertEquals(0xFFFF0000, Utils.getColorInt("#F00"));
        assertEquals(0xFF00FF00, Utils.getColorInt("#0F0"));
        assertEquals(0xFF0000FF, Utils.getColorInt("#00F"));
    }

    @Test
    public void getColorInt_with_length_6() throws Exception {
        assertEquals(0xFFFFFFFF, Utils.getColorInt("#FFFFFF"));
        assertEquals(0xFFFF0000, Utils.getColorInt("#FF0000"));
        assertEquals(0xFF00FF00, Utils.getColorInt("#00FF00"));
        assertEquals(0xFF0000FF, Utils.getColorInt("#0000FF"));
    }

    @Test
    public void getColorInt_with_length_8() throws Exception {
        assertEquals(0xABFFFFFF, Utils.getColorInt("#ABFFFFFF"));
        assertEquals(0xFFFF0000, Utils.getColorInt("#FFFF0000"));
        assertEquals(0x00000000, Utils.getColorInt("#00000000"));
        assertEquals(0x000000FF, Utils.getColorInt("#000000FF"));
    }

    @Test
    public void getColorInt_throws_when_non_hex() throws Exception {
        boolean thrown = false;

        try {
            Utils.getColorInt("R.color.test");
        } catch (NumberFormatException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            Utils.getColorInt("0f0f0f");
        } catch (NumberFormatException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            Utils.getColorInt("blah blah");
        } catch (NumberFormatException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void getColorInt_throws_when_invalid_length() throws Exception {
        boolean thrown = false;
        try {
            Utils.getColorInt("#00");
        } catch (BadParameterException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            Utils.getColorInt("#0000");
        } catch (BadParameterException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            Utils.getColorInt("#00000");
        } catch (BadParameterException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            Utils.getColorInt("#0000000");
        } catch (BadParameterException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            Utils.getColorInt("#000000000");
        } catch (BadParameterException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
