package com.burhangok.listmovies.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.burhangok.listmovies.R;

public class SplashScreenActivity extends Activity {

    Intent toMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(3000);
                }
                catch (Exception e) {

                }

                finally {
                    toMain = new Intent(SplashScreenActivity.this,MainActivity.class);
                    startActivity(toMain);

                }
            }
        }).start();
    }
}
