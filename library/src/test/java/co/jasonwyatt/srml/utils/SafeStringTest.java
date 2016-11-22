package co.jasonwyatt.srml.utils;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class SafeStringTest {
    @Test
    public void equals_returns_true() throws Exception {
        SafeString a = new SafeString("this is a test");
        SafeString b = new SafeString("this is a test");

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    @Test
    public void equals_returns_false() throws Exception {
        SafeString a = new SafeString("this is a test");
        SafeString b = new SafeString("this is another test");

        assertFalse(a.equals(b));
        assertFalse(b.equals(a));
        assertFalse(a.equals(null));
        assertFalse(a.equals(5));
        assertFalse(a.equals("this is a test"));
    }

    @Test
    public void hashCode_works() throws Exception {
        SafeString a = new SafeString("this is a test");
        SafeString b = new SafeString("this is a test");
        SafeString c = new SafeString("this is a third test");

        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(a.hashCode(), "this is a test".hashCode());
        assertFalse(a.hashCode() == c.hashCode());
    }
}
