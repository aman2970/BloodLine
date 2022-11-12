package com.example.blooddonationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView logo;
    private TextView app_name, app_slogan;
    private int SPLASH_SCREEN_TIME = 4300;

    Animation topAnimation , bottomAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.app_logo);
        app_name = findViewById(R.id.app_name_tv);
        app_slogan =findViewById(R.id.app_slogan_tv);

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logo.setAnimation(topAnimation);
        app_name.setAnimation(bottomAnimation);
        app_slogan.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_TIME);

    }
}