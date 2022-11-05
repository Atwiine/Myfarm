package com.example.farm.MatookeSection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farm.Adapters.MatookAdapter;
import com.example.farm.Adapters.MilkResultsAdapter;
import com.example.farm.AnimalSection.AnimalManagement;
import com.example.farm.AnimalSection.MilkActivity;
import com.example.farm.MainActivity;
import com.example.farm.Modals.MatookeModel;
import com.example.farm.Modals.MilkResultsModel;
import com.example.farm.R;
import com.example.farm.Urls.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Matooke extends AppCompatActivity {

    RecyclerView recyclerView;
    List<MatookeModel> mData;
    MatookAdapter adapter;
    TextView error_message_balance, no_message_balance,  total;
    Urls urls;
    String getID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matookeesults);

        urls = new Urls();
        error_message_balance = findViewById(R.id.error_message_balance);
        no_message_balance = findViewById(R.id.no_message_balance);


        recyclerView = findViewById(R.id.recyclerview_matooke_results);
        mData = new ArrayList<>();
//        mData.add(new MatookeModel("1", "20",  "9/24/2022"));
//        mData.add(new MatookeModel("1", "730",  "9/24/2022"));
//        mData.add(new MatookeModel("1", "2930",  "9/24/2022"));
//        mData.add(new MatookeModel("1", "60", "9/24/2022"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MatookAdapter(this, mData);
        recyclerView.setAdapter(adapter);

        loadMatookeesults();
    }

    public void goBack(View view) {
        startActivity(new Intent(Matooke.this, MilkMatookeMgt.class));
    }

    private void loadMatookeesults() {
        final ProgressDialog progressDialog = new ProgressDialog(Matooke.this);
        progressDialog.setMessage("Loading results, please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_MATOOKE_RESULTS,
                response -> {
                    progressDialog.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                        if (tips.length() == 0) {
                            no_message_balance.setVisibility(View.VISIBLE);
                            total.setText("0");
                        } else {
                            for (int i = 0; i < tips.length(); i++) {
                                JSONObject inputsObjects = tips.getJSONObject(i);

                                String id = inputsObjects.getString("id");
                                String total = inputsObjects.getString("total");
                                String date = inputsObjects.getString("date");

                                MatookeModel inputsModel =
                                        new MatookeModel(id, total, date
                                        );
                                mData.add(inputsModel);
                            }
                            adapter = new MatookAdapter(Matooke.this, mData);
                            recyclerView.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        error_message_balance.setVisibility(View.VISIBLE);
                        error_message_balance.setText(e.toString());
                        // Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            progressDialog.dismiss();
            error_message_balance.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "Something went wrong, check your connection and try again please try again", Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("", "");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}