package co.jasonwyatt.srml.tags;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by jason on 11/11/16.
 */

public class ColorTest {
    @Test
    public void getColorInt_with_length_3() throws Exception {
        assertEquals(0xFFFFFFFF, Color.getColorInt("#FFF"));
        assertEquals(0xFFFF0000, Color.getColorInt("#F00"));
        assertEquals(0xFF00FF00, Color.getColorInt("#0F0"));
        assertEquals(0xFF0000FF, Color.getColorInt("#00F"));
    }

    @Test
    public void getColorInt_with_length_6() throws Exception {
        assertEquals(0xFFFFFFFF, Color.getColorInt("#FFFFFF"));
        assertEquals(0xFFFF0000, Color.getColorInt("#FF0000"));
        assertEquals(0xFF00FF00, Color.getColorInt("#00FF00"));
        assertEquals(0xFF0000FF, Color.getColorInt("#0000FF"));
    }

    @Test
    public void getColorInt_with_length_8() throws Exception {
        assertEquals(0xABFFFFFF, Color.getColorInt("#ABFFFFFF"));
        assertEquals(0xFFFF0000, Color.getColorInt("#FFFF0000"));
        assertEquals(0x00000000, Color.getColorInt("#00000000"));
        assertEquals(0x000000FF, Color.getColorInt("#000000FF"));
    }

    @Test
    public void getColorInt_throws_when_invalid_length() throws Exception {
        boolean thrown = false;
        try {
            Color.getColorInt("#00");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            Color.getColorInt("#0000");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            Color.getColorInt("#00000");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            Color.getColorInt("#0000000");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            Color.getColorInt("#000000000");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }
}
