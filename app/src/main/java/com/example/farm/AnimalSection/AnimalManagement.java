package com.example.farm.AnimalSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farm.MainActivity;
import com.example.farm.R;

public class AnimalManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animalmanagement);
    }

    public void goBack(View view) {
        startActivity(new Intent(AnimalManagement.this, MainActivity.class));
    }

    public void openCattleSection(View view) {
        String type = "Cattle";
        Intent gg = new Intent(AnimalManagement.this,AnimalResults.class);
        gg.putExtra("type",type);
        startActivity(gg);
    }



    public void openGoatsSection(View view) {
        String type = "Goat";
        Intent gg = new Intent(AnimalManagement.this,AnimalResults.class);
        gg.putExtra("type",type);
        startActivity(gg);
    }
}