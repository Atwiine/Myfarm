package com.example.farm.MatookeSection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.farm.R;
import com.example.farm.Urls.Urls;

public class AddEmployees extends AppCompatActivity {


    TextView error_message_balance, no_message_balance, heading, no_message_balance_selected_alpha, total;
//    SessionManager sessionManager;
    Urls urls;
    String getID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addemployees);

        urls = new Urls();

    }

    public void goBack(View view) {
        startActivity(new Intent(AddEmployees.this, Matooke.class));
    }



}