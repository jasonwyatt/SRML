package co.jasonwyatt.srml.tags;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class IntentTagTest {
    private Bundle mBundle;

    @Before
    public void setup() {
        mBundle = mock(Bundle.class);
    }

    @Test
    public void parseAndSetExtra_string() {
        IntentTag.parseAndSetExtra(mBundle, "key1", "testvalue");
        verify(mBundle).putString("key1", "testvalue");

        IntentTag.parseAndSetExtra(mBundle, "key2", "");
        verify(mBundle).putString("key2", "");
    }

    @Test
    public void parseAndSetExtra_unsupportedCast() {
        IntentTag.parseAndSetExtra(mBundle, "key", "coolclass(value)");
        verify(mBundle).putString("key", "coolclass(value)");
    }

    @Test
    public void parseAndSetExtra_boolean() {
        IntentTag.parseAndSetExtra(mBundle, "true", "true");
        verify(mBundle).putBoolean("true", true);

        IntentTag.parseAndSetExtra(mBundle, "false", "false");
        verify(mBundle).putBoolean("false", false);
    }

    @Test
    public void parseAndSetExtra_byte() {
        IntentTag.parseAndSetExtra(mBundle, "zero", "byte(0)");
        verify(mBundle).putByte("zero", (byte) 0);

        IntentTag.parseAndSetExtra(mBundle, "one", "byte(1)");
        verify(mBundle).putByte("one", (byte) 1);

        IntentTag.parseAndSetExtra(mBundle, "neg_one", "byte(-1)");
        verify(mBundle).putByte("neg_one", (byte) -1);

        try {
            IntentTag.parseAndSetExtra(mBundle, "overflow", "byte(256)");
        } catch (BadParameterException e) {
            assertTrue(e.getCause() instanceof NumberFormatException);
        }
    }

    @Test
    public void parseAndSetExtra_short() {
        IntentTag.parseAndSetExtra(mBundle, "zero", "short(0)");
        verify(mBundle).putShort("zero", (short) 0);

        IntentTag.parseAndSetExtra(mBundle, "one", "short(1)");
        verify(mBundle).putShort("one", (short) 1);

        IntentTag.parseAndSetExtra(mBundle, "neg_one", "short(-1)");
        verify(mBundle).putShort("neg_one", (short) -1);

        try {
            IntentTag.parseAndSetExtra(mBundle, "overflow", "short(65536)");
        } catch (BadParameterException e) {
            assertTrue(e.getCause() instanceof NumberFormatException);
        }
    }

    @Test
    public void parseAndSetExtra_int() {
        IntentTag.parseAndSetExtra(mBundle, "zero", "int(0)");
        verify(mBundle).putInt("zero", 0);

        IntentTag.parseAndSetExtra(mBundle, "one", "int(1)");
        verify(mBundle).putInt("one", 1);

        IntentTag.parseAndSetExtra(mBundle, "neg_one", "int(-1)");
        verify(mBundle).putInt("neg_one", -1);

        try {
            IntentTag.parseAndSetExtra(mBundle, "overflow", "int(5000000000)");
        } catch (BadParameterException e) {
            assertTrue(e.getCause() instanceof NumberFormatException);
        }
    }

    @Test
    public void parseAndSetExtra_long() {
        IntentTag.parseAndSetExtra(mBundle, "zero", "long(0)");
        verify(mBundle).putLong("zero", 0);

        IntentTag.parseAndSetExtra(mBundle, "one", "long(1)");
        verify(mBundle).putLong("one", 1);

        IntentTag.parseAndSetExtra(mBundle, "neg_one", "long(-1)");
        verify(mBundle).putLong("neg_one", -1);

        try {
            IntentTag.parseAndSetExtra(mBundle, "overflow", "long(10000000000000000000)");
        } catch (BadParameterException e) {
            assertTrue(e.getCause() instanceof NumberFormatException);
        }
    }

    @Test
    public void parseAndSetExtra_float() {
        IntentTag.parseAndSetExtra(mBundle, "zero", "float(0)");
        verify(mBundle).putFloat("zero", 0);

        IntentTag.parseAndSetExtra(mBundle, "zero.zero", "float(0.0)");
        verify(mBundle).putFloat("zero.zero", 0.0f);

        IntentTag.parseAndSetExtra(mBundle, "zero.five", "float(0.5)");
        verify(mBundle).putFloat("zero.five", 0.5f);

        IntentTag.parseAndSetExtra(mBundle, "one", "float(1)");
        verify(mBundle).putFloat("one", 1);

        IntentTag.parseAndSetExtra(mBundle, "neg_one", "float(-1)");
        verify(mBundle).putFloat("neg_one", -1);
    }

    @Test
    public void parseAndSetExtra_double() {
        IntentTag.parseAndSetExtra(mBundle, "zero", "double(0)");
        verify(mBundle).putDouble("zero", 0);

        IntentTag.parseAndSetExtra(mBundle, "zero.zero", "double(0.0)");
        verify(mBundle).putDouble("zero.zero", 0.0);

        IntentTag.parseAndSetExtra(mBundle, "zero.five", "double(0.5)");
        verify(mBundle).putDouble("zero.five", 0.5);

        IntentTag.parseAndSetExtra(mBundle, "one", "double(1)");
        verify(mBundle).putDouble("one", 1);

        IntentTag.parseAndSetExtra(mBundle, "neg_one", "double(-1)");
        verify(mBundle).putDouble("neg_one", -1);
    }

    @Test
    public void parseAndSetExtra_char() {
        IntentTag.parseAndSetExtra(mBundle, "a", "char(a)");
        verify(mBundle).putChar("a", 'a');

        IntentTag.parseAndSetExtra(mBundle, "sixtyfour", "char(64)");
        verify(mBundle).putChar("sixtyfour", (char) 64);

        try {
            IntentTag.parseAndSetExtra(mBundle, "overflow", "char(256)");
        } catch (BadParameterException e) {
            assertTrue(e.getCause() instanceof ClassCastException);
        }
    }
}
