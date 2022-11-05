package com.example.farm.AnimalSection;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farm.Adapters.AnimalResultsAdapter;
import com.example.farm.Adapters.MilkResultsAdapter;
import com.example.farm.Modals.AnimalResultsModel;
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


public class AnimalResults extends AppCompatActivity {

    RecyclerView recyclerView;
    List<AnimalResultsModel> mData;
    AnimalResultsAdapter adapter;
    TextView error_message_balance, no_message_balance, selectedtype;
//    SessionManager sessionManager;
    Urls urls;
    String getID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animalresults);

        urls = new Urls();
        error_message_balance = findViewById(R.id.error_message_balance);
        no_message_balance = findViewById(R.id.no_message_balance);
        selectedtype = findViewById(R.id.selectedtype);

        /*receive the selected time frame*/
        String selectedtypes = getIntent().getStringExtra("type");
        if (selectedtypes.equals("Goat")){
            selectedtype.setText(selectedtypes+"s");
        }else {
            selectedtype.setText(selectedtypes);
        }



//change the modal and use the sent intent to search shit against it
        recyclerView = findViewById(R.id.recyclerview_animal_results);
        mData = new ArrayList<>();
//        mData.add(new AnimalResultsModel("123U-HH", "Cow", "43", "9/24/2022", "200", "No"));
//        mData.add(new AnimalResultsModel("87863U-HH", "Cow", "43", "9/24/2022", "200", "Yes"));
//        mData.add(new AnimalResultsModel("1353U-HH", "Bull", "43", "9/24/2022", "200", "No"));
//        mData.add(new AnimalResultsModel("22243U-HH", "Cow", "43", "9/24/2022", "200", "Yes"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AnimalResultsAdapter(this, mData);
        recyclerView.setAdapter(adapter);

        /*loading the results*/
        loadResults(selectedtypes);
    }

    public void goBack(View view) {
        startActivity(new Intent(AnimalResults.this, AnimalManagement.class));
    }


    private void loadResults(String selectedtype) {
        final ProgressDialog progressDialog = new ProgressDialog(AnimalResults.this);
        progressDialog.setMessage("Loading results, please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_ANIMAL_RESULTS,
                response -> {
                    progressDialog.dismiss();
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray tips = new JSONArray(response);
                        if (tips.length() == 0) {
                            no_message_balance.setVisibility(View.VISIBLE);
                        } else {
                            for (int i = 0; i < tips.length(); i++) {
                                JSONObject inputsObjects = tips.getJSONObject(i);
                                String id = inputsObjects.getString("id");
                                String tagnumber = inputsObjects.getString("tagnumber");
                                String gender = inputsObjects.getString("gender");
                                String weight = inputsObjects.getString("weight");
                                String date = inputsObjects.getString("date");
                                String checker = inputsObjects.getString("checkere");//for checking if this animal has young ones
                                String parent_tagnumber = inputsObjects.getString("parent_tagnumber");

                                AnimalResultsModel inputsModel =
                                        new AnimalResultsModel(tagnumber, gender, id,date,weight,checker,parent_tagnumber
                                        );
                                mData.add(inputsModel);
                            }
                            adapter = new AnimalResultsAdapter(AnimalResults.this, mData);
                            recyclerView.setAdapter(adapter);
                        }
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Toast.makeText(this, "Sometlease try again", Toast.LENGTH_SHORT).show();
                        error_message_balance.setVisibility(View.VISIBLE);
                        error_message_balance.setText(e.toString());
                    }
                }, error -> {
            progressDialog.dismiss();
            error_message_balance.setVisibility(View.VISIBLE);
            error_message_balance.setText(error.toString());
            Toast.makeText(this, "Something went wrong, check your connection and try again please try again", Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("selectedtype", selectedtype);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}