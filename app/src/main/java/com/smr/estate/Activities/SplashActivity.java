package com.smr.estate.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.smr.estate.R;
import com.smr.estate.Storage.Read;

//Check register user with splash

public class SplashActivity extends AppCompatActivity
{
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        user_id = Read.readDataFromStorage("user_id", "0", getApplicationContext());

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (!user_id.equals("0"))
                {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                } else
                {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    SplashActivity.this.finish();
                }

            }
        }, 1000);
    }
}
