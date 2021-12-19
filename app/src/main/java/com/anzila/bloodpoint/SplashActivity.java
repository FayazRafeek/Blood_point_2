package com.anzila.bloodpoint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anzila.bloodpoint.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {


    ActivitySplashBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        SharedPreferences prefs = getSharedPreferences("MyPref",MODE_PRIVATE);
                        Boolean isLogin = prefs.getBoolean("IS_LOGIN",false);
                        if(isLogin)
                            startActivity(new Intent(SplashActivity.this,homeActivity.class));
                        else
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));

                        finish();
                    }
                },1500);
    }
}
