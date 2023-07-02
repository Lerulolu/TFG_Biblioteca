package com.example.tfg_biblioteca.PantallasApp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg_biblioteca.R;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 2000; // Duración en milisegundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_DELAY);
    }
}
