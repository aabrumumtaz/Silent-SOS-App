package com.example.silentsos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 2.5 seconds (2500 milliseconds) ka delay taake splash screen show ho sake
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // LoginActivity par redirect kar dein
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Splash activity close kar dein taake back karne par wapas na aaye
            }
        }, 2500);
    }
}