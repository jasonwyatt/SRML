package co.jasonwyatt.srml;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.Html;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

import static org.junit.Assert.assertTrue;

/**
 * @author jason
 *
 * Performance tests. They fail if {@link SRML#getString} is slower than {@link Html#fromHtml}.
 */
@RunWith(AndroidJUnit4.class)
public class PerformanceTest {
    private static final String TAG = PerformanceTest.class.getSimpleName();

    @Test
    public void testSRMLvsHTMLLegacy() {
        int iterations = 100000;
        Context context = InstrumentationRegistry.getContext();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            SRML.getString(context, R.string.simple_srml_string);
        }
        long elapsedSRML = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            Html.fromHtml(context.getString(R.string.simple_html_string));
        }
        long elapsedHTML = System.currentTimeMillis() - startTime;

        double srmlTimePer = elapsedSRML / (double) iterations;
        double htmlTimePer = elapsedHTML / (double) iterations;
        Log.i(TAG, String.format(Locale.US, "SRML.getString averaged %.2fms", srmlTimePer));
        Log.i(TAG, String.format(Locale.US, "Html.fromHtml averaged %.2fms", htmlTimePer));
        assertTrue("SRML.getString should be as fast, or faster than, Html.fromHtml.", srmlTimePer <= htmlTimePer);
    }

    @Test
    public void testSRMLvsHTMLLegacyWithParam() {
        int iterations = 100000;
        Context context = InstrumentationRegistry.getContext();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            SRML.getString(context, R.string.simple_srml_string_with_param, Integer.toString(i));
        }
        long elapsedSRML = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            Html.fromHtml(context.getString(R.string.simple_html_string_with_param, Integer.toString(i)));
        }
        long elapsedHTML = System.currentTimeMillis() - startTime;

        double srmlTimePer = elapsedSRML / (double) iterations;
        double htmlTimePer = elapsedHTML / (double) iterations;
        Log.i(TAG, String.format(Locale.US, "SRML.getString averaged %.2fms", srmlTimePer));
        Log.i(TAG, String.format(Locale.US, "Html.fromHtml averaged %.2fms", htmlTimePer));
        assertTrue("SRML.getString should be as fast, or faster than, Html.fromHtml.", srmlTimePer <= htmlTimePer);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Test
    public void testSRMLvsHTMLLegacyWithDirtyParam() {
        int iterations = 100000;
        Context context = InstrumentationRegistry.getContext();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            SRML.getString(context, R.string.simple_srml_string_with_param, String.format(Locale.US, "{{b}}%d{{/b}}", i));
        }
        long elapsedSRML = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        for (int i = 0; i < iterations; i++) {
            Html.fromHtml(context.getString(R.string.simple_html_string_with_param, Html.escapeHtml(String.format(Locale.US, "<b>%d</b>", i))));
        }
        long elapsedHTML = System.currentTimeMillis() - startTime;

        double srmlTimePer = elapsedSRML / (double) iterations;
        double htmlTimePer = elapsedHTML / (double) iterations;
        Log.i(TAG, String.format(Locale.US, "SRML.getString averaged %.2fms", srmlTimePer));
        Log.i(TAG, String.format(Locale.US, "Html.fromHtml averaged %.2fms", htmlTimePer));
        assertTrue("SRML.getString should be as fast, or faster than, Html.fromHtml.", srmlTimePer <= htmlTimePer);
    }
}
