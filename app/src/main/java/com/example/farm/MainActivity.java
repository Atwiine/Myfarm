package com.example.farm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.farm.AnimalSection.AnimalManagement;
import com.example.farm.Chat.SinglesChat;
import com.example.farm.Employees.Employees;
import com.example.farm.MatookeSection.Matooke;
import com.example.farm.MatookeSection.MilkMatookeMgt;
import com.example.farm.Urls.SessionManager;

public class MainActivity extends AppCompatActivity {
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
    }

    public void openAnimalManagement(View view) {
        startActivity(new Intent(MainActivity.this, AnimalManagement.class));
    }

    public void openMatMilkManagement(View view) {
        startActivity(new Intent(MainActivity.this, MilkMatookeMgt.class));
    }

    public void openEmployeeManagement(View view) {
        startActivity(new Intent(MainActivity.this, Employees.class));

    }

    public void openChat(View view) {
        startActivity(new Intent(MainActivity.this, SinglesChat.class));

    }

    public void openSettings(View view) {
        SettingsActivity settingsActivity = new SettingsActivity();
        settingsActivity.show(getSupportFragmentManager(),
                "ModalBottomSheet");
    }
}