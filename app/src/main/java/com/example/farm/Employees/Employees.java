package com.example.farm.Employees;

import android.annotation.SuppressLint;
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
import com.example.farm.Adapters.EmployeeAdapter;
import com.example.farm.Adapters.MatookAdapter;
import com.example.farm.MainActivity;
import com.example.farm.MatookeSection.MilkMatookeMgt;
import com.example.farm.Modals.EmployeeModel;
import com.example.farm.Modals.MatookeModel;
import com.example.farm.R;
import com.example.farm.Urls.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Employees extends AppCompatActivity {

    RecyclerView recyclerView;
    List<EmployeeModel> mData;
    EmployeeAdapter adapter;
    TextView error_message_balance, no_message_balance, total;
    Urls urls;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        urls = new Urls();
        error_message_balance = findViewById(R.id.error_message_balance);
        no_message_balance = findViewById(R.id.no_message_balance);


        recyclerView = findViewById(R.id.recyclerview_employee_results);
        mData = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmployeeAdapter(this, mData);
        recyclerView.setAdapter(adapter);

        loadEmployees();
    }

    public void goBack(View view) {
        startActivity(new Intent(Employees.this, MainActivity.class));
    }

    private void loadEmployees() {
        final ProgressDialog progressDialog = new ProgressDialog(Employees.this);
        progressDialog.setMessage("Loading employees, please wait....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOAD_EMPLOYEES,
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
                                String name = inputsObjects.getString("name");
                                String number = inputsObjects.getString("number");
                                String role = inputsObjects.getString("role");
                                String address = inputsObjects.getString("address");
                                String gender = inputsObjects.getString("gender");
                                String nextofkin = inputsObjects.getString("nextofkin");
                                String contactnextofkim = inputsObjects.getString("contactnextofkim");

                                EmployeeModel inputsModel =
                                        new EmployeeModel(id, name, number, role, address, gender,nextofkin,contactnextofkim
                                        );
                                mData.add(inputsModel);
                            }
                            adapter = new EmployeeAdapter(Employees.this, mData);
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