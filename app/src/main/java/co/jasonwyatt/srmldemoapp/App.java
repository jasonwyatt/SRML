package co.jasonwyatt.srmldemoapp;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import co.jasonwyatt.srml.SRML;
import co.jasonwyatt.srml.SRMLImageLoader;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SRML.setImageLoader(new SRMLImageLoader() {
            @Override
            public Bitmap loadImage(Context context, String url) {
                try {
                    return Picasso.with(context).load(url).get();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Bitmap loadImage(Context context, String url, int width, int height) {
                try {
                    return Picasso.with(context).load(url).resize(width, height).get();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
