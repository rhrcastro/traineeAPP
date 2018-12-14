package bsi.mpoo.traineeufrpe.gui.main;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import bsi.mpoo.traineeufrpe.R;

public class ActSplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        loadGif();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(ActSplashScreen.this, ActHome.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

    private void loadGif() {
        Glide.with(getBaseContext())
                .load(R.drawable.preloader)
                .asGif()
                .into((ImageView) findViewById(R.id.gif_load));
    }
}
