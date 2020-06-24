package com.mjc.translate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreference = new SharedPreference(this);
        // 첫 시작 판별
        if(!sharedPreference.getSharedboolean("first")){
            sharedPreference.setSharedboolean("autoSave", true);
            sharedPreference.setSharedInteger("fontSize", 20);
            sharedPreference.setSharedboolean("first", true);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.anim_alpha_in, R.anim.anim_alpha_out);
                finish();
            }
        }, 2000);


    }
}
