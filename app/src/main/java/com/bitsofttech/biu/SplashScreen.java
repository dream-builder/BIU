package com.bitsofttech.biu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        SplashThread splashThread = new SplashThread();
        splashThread.start();
    }

    class SplashThread extends Thread{

        @Override
        public void run() {
            super.run();

            try {
                sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finally {
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }


        }
    }
}

