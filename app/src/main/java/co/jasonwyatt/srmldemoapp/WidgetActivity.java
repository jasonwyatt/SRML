package co.jasonwyatt.srmldemoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class WidgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);

        ((TextView)findViewById(R.id.srml_text_view)).setMovementMethod(LinkMovementMethod.getInstance());
    }
}
