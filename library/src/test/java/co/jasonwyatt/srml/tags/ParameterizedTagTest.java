package co.jasonwyatt.srml.tags;

import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import co.jasonwyatt.srml.utils.Utils;

import static junit.framework.Assert.assertEquals;

public class ParameterizedTagTest {
    @Test
    public void parseParams_single() {
        Map<String, String> out = new HashMap<>();
        ParameterizedTag.parseParams("tag test=value", out);

        assertEquals("value", out.get("test"));
    }

    @Test
    public void parseParams_quoted() {
        Map<String, String> out = new HashMap<>();
        ParameterizedTag.parseParams("tag test=`value is a string`", out);

        assertEquals("value is a string", out.get("test"));
    }

    @Test
    public void parseParams_quotedMultiple() {
        Map<String, String> out = new HashMap<>();
        ParameterizedTag.parseParams("tag test=`value is a string` test2=`this is another string`", out);

        assertEquals("value is a string", out.get("test"));
        assertEquals("this is another string", out.get("test2"));
    }

    @Test
    public void parseParams_multiple() {
        Map<String, String> out = new HashMap<>();
        ParameterizedTag.parseParams("tag test=value test2=3243 test_3=1124", out);

        assertEquals("value", out.get("test"));
        assertEquals("3243", out.get("test2"));
        assertEquals("1124", out.get("test_3"));
    }

    @Test
    public void getParamsMatching() {
        Map<String, String> params = new HashMap<>();
        params.put("x_first", "test");
        params.put("x_second", "test_2");
        params.put("another", "value");
        params.put("something_else", "blah");

        List<Utils.Pair<String, String>> matchedParams = ParameterizedTag.getParamsMatching(Pattern.compile("x_[^=]+"), params);
        assertEquals(2, matchedParams.size());

        Collections.sort(matchedParams, new Comparator<Utils.Pair<String, String>>() {
            @Override
            public int compare(Utils.Pair<String, String> o1, Utils.Pair<String, String> o2) {
                return o1.first.compareTo(o2.first);
            }
        });

        assertEquals("x_first", matchedParams.get(0).first);
        assertEquals("x_second", matchedParams.get(1).first);
        assertEquals("test", matchedParams.get(0).second);
        assertEquals("test_2", matchedParams.get(1).second);
    }
}
