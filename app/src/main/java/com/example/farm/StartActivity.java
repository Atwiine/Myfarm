package com.example.farm;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farm.Register.Login;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(StartActivity.this, MainActivity.class);
                startActivity(i);
                // close this activity
                finish();
            }
        }, 3000);

    }

    public void openLogin(View view) {
        startActivity(new Intent(StartActivity.this, Login.class));
    }
}