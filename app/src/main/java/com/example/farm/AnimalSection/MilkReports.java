package com.example.farm.AnimalSection;

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
import com.example.farm.Adapters.MilkResultsAdapter;
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

public class MilkReports extends AppCompatActivity {

    RecyclerView recyclerView;
    List<MilkResultsModel> mData;
    MilkResultsAdapter adapter;
    TextView error_message_balance, no_message_balance, heading, no_message_balance_selected_alpha, total;
//    SessionManager sessionManager;
    Urls urls;
    String getID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milkreports);

        urls = new Urls();
        error_message_balance = findViewById(R.id.error_message_balance);
        no_message_balance = findViewById(R.id.no_message_balance);

        /*receive the selected time frame*/
//        String selectedtime = getIntent().getStringExtra("time");

        /*check for the selected time frame and search against that and the current date*/
//        if (selectedtime.equals("Morning")){
//            loadMilkingResults(selectedtime);
//        }else  if (selectedtime.equals("Afternoon")){
//            loadMilkingResults(selectedtime);
//        }else  if (selectedtime.equals("Evening")){
//            loadMilkingResults(selectedtime);
//        }


//        recyclerView = findViewById(R.id.recyclerview_milking_results);
//        mData = new ArrayList<>();
//        mData.add(new MilkResultsModel("1", "230", "30", "200", "", "9/24/2022"));
//        mData.add(new MilkResultsModel("1", "230", "30", "200", "", "9/24/2022"));
//        mData.add(new MilkResultsModel("1", "230", "30", "200", "", "9/24/2022"));
//        mData.add(new MilkResultsModel("1", "230", "30", "200", "", "9/24/2022"));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new MilkResultsAdapter(this, mData);
//        recyclerView.setAdapter(adapter);

    }

    public void goBack(View view) {
        startActivity(new Intent(MilkReports.this, MilkActivity.class));
    }

    private void loadMilkingResults(String selectedtime) {
        final ProgressDialog progressDialog = new ProgressDialog(MilkReports.this);
        progressDialog.setMessage("Loading results, please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_MILKING_RESULTS,
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
                                String home = "0" + inputsObjects.getString("home");
                                String diary = inputsObjects.getString("diary");
                                String date = inputsObjects.getString("date");
                                String comment = inputsObjects.getString("comment");
                                String timesent = inputsObjects.getString("timesent");


                                MilkResultsModel inputsModel =
                                        new MilkResultsModel(id, total, home, diary, comment, date,timesent
                                        );
                                mData.add(inputsModel);
                            }
                            adapter = new MilkResultsAdapter(MilkReports.this, mData);
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
                params.put("userid", getID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}