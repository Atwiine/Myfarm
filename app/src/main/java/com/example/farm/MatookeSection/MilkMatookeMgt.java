package com.example.farm.MatookeSection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farm.Adapters.MilkResultsAdapter;
import com.example.farm.AnimalSection.AnimalManagement;
import com.example.farm.AnimalSection.MilkActivity;
import com.example.farm.MainActivity;
import com.example.farm.Modals.MilkResultsModel;
import com.example.farm.R;
import com.example.farm.Urls.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MilkMatookeMgt extends AppCompatActivity {

    //    SessionManager sessionManager;
    Urls urls;
    String getID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matokmgt);
        urls = new Urls();
    }

    public void goBack(View view) {
        startActivity(new Intent(MilkMatookeMgt.this, MainActivity.class));
    }

    public void openMilkSection(View view) {
        startActivity(new Intent(MilkMatookeMgt.this, MilkActivity.class));
    }

    public void openMatookeSection(View view) {
        startActivity(new Intent(MilkMatookeMgt.this, Matooke.class));
    }
}