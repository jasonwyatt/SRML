package co.jasonwyatt.srml.tags;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class ParameterizedTagTest {
    @Test
    public void parseParams_single() {
        Map<String, String> out = new HashMap<>();
        ParameterizedTag.parseParams("tag test=value", out);

        assertEquals("value", out.get("test"));
    }

    @Test
    public void parseParams_multiple() {
        Map<String, String> out = new HashMap<>();
        ParameterizedTag.parseParams("tag test=value test2=3243 test_3=1124", out);

        assertEquals("value", out.get("test"));
        assertEquals("3243", out.get("test2"));
        assertEquals("1124", out.get("test_3"));
    }
}
