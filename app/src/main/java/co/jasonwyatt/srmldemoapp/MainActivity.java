package co.jasonwyatt.srmldemoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import co.jasonwyatt.srml.SRML;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)findViewById(R.id.bold1)).setText(SRML.getString(this, R.string.bold_test));
        ((TextView)findViewById(R.id.bold2)).setText(SRML.getString(this, R.string.bold_test_oops));
        ((TextView)findViewById(R.id.italic1)).setText(SRML.getString(this, R.string.italic_test));
        ((TextView)findViewById(R.id.italic2)).setText(SRML.getString(this, R.string.italic_test_oops));
        ((TextView)findViewById(R.id.bold_italic1)).setText(SRML.getString(this, R.string.bold_italic_test));
        ((TextView)findViewById(R.id.bold_italic2)).setText(SRML.getString(this, R.string.bold_italic_test_oops));
        ((TextView)findViewById(R.id.underline)).setText(SRML.getString(this, R.string.underline_test));
        ((TextView)findViewById(R.id.strike)).setText(SRML.getString(this, R.string.strike_test));
    }
}
