package co.jasonwyatt.srmldemoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import co.jasonwyatt.srml.SRML;

public class TargetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        setTitle(R.string.another_activity_title);

        Intent i = getIntent();
        TextView textView = (TextView) findViewById(R.id.text);
        if (i.hasExtra("text")) {
            textView.setText(SRML.getString(this, R.string.text_extra_sent, i.getStringExtra("text")));
        } else {
            textView.setText(SRML.getString(this, R.string.no_text_extra_sent));
        }
    }
}
