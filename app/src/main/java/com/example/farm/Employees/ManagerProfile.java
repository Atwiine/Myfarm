package com.example.farm.Employees;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farm.R;
import com.example.farm.Urls.SessionManager;
import com.example.farm.Urls.Urls;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ManagerProfile extends AppCompatActivity implements Spinner.OnItemSelectedListener {


    TextView addmanager, editmanager, error_message_balance, idd, total;
    SessionManager sessionManager;
    Urls urls;
    String getID, farmname, dd;
    EditText username, contact, nextkin, nextkincontact, address, age, genderss;
    LottieAnimationView animationView;
    Dialog loadingUI;
    String rr = "Manager";
    MaterialButton cardEdit, cardAdd;
    Spinner gender;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);
        urls = new Urls();
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        getID = user.get(SessionManager.ID);
        farmname = user.get(SessionManager.FARMNAME);

        address = findViewById(R.id.address);
        username = findViewById(R.id.username);
        contact = findViewById(R.id.contact);
        nextkin = findViewById(R.id.nextkin);
        nextkincontact = findViewById(R.id.nextkincontact);
        addmanager = findViewById(R.id.addmanager);
        editmanager = findViewById(R.id.editmanager);
        error_message_balance = findViewById(R.id.error_message_balance);
        idd = findViewById(R.id.idd);
        cardEdit = findViewById(R.id.cardEdit);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        genderss = findViewById(R.id.genderss);
        cardAdd = findViewById(R.id.cardAdd);


        address.setText(getIntent().getStringExtra("address"));
        username.setText(getIntent().getStringExtra("name"));
        contact.setText(getIntent().getStringExtra("number"));
        nextkin.setText(getIntent().getStringExtra("nextofkin"));
        nextkincontact.setText(getIntent().getStringExtra("contactnextofkim"));
        age.setText(getIntent().getStringExtra("from"));
        idd.setText(getIntent().getStringExtra("id"));
        age.setText(getIntent().getStringExtra("age"));
        genderss.setText(getIntent().getStringExtra("gender"));


        // check if the feilds are empty and then tell the user to add the profile or else show the edit options
        dd = idd.getText().toString();


        if (dd.equals("")) {
            editmanager.setVisibility(View.GONE);
            addmanager.setVisibility(View.VISIBLE);
        } else {
            addmanager.setVisibility(View.GONE);
            editmanager.setVisibility(View.VISIBLE);
            genderss.setVisibility(View.VISIBLE);
            cardEdit.setVisibility(View.VISIBLE);
            cardAdd.setVisibility(View.GONE);

        }

//        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                Object item = parent.getItemAtPosition(pos);
//                Toast.makeText(ManagerProfile.this, "aa "+item, Toast.LENGTH_SHORT).show();
//                genderss.setText(item.toString());
//            }
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        gender.setOnItemSelectedListener(ManagerProfile.this);
    }


    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView spinner_text = (TextView) view;
//        Toast.makeText(this, "Your grade is " + spinner_text.getText(), Toast.LENGTH_SHORT).show();
        genderss.setText(gender.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void goBack(View view) {
        startActivity(new Intent(ManagerProfile.this, Employees.class));
    }

    private void loadManager(String rolle, String getID) {

        loadingUI = new Dialog(this);
        loadingUI.setContentView(R.layout.right);
        animationView = loadingUI.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView
                .playAnimation();
        loadingUI.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.MANAGER_PROFILE,
                response -> {

                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
//                        JSONArray tips = new JSONArray(response);
                        JSONObject jsonObject = new JSONObject(response);
                        @SuppressLint("NotConstructor") String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("login");
                        if (success.equals("1")) {
                            loadingUI.dismiss();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject inputsObjects = jsonArray.getJSONObject(i);
                                String id = inputsObjects.getString("id");
                                String name = inputsObjects.getString("name");
                                String number = "0" + inputsObjects.getString("number");
                                String addresss = inputsObjects.getString("address");
                                String nextofkin = inputsObjects.getString("nextofkin");
                                String contactnextofkim = "0" + inputsObjects.getString("contactnextofkim");

                                idd.setText(id);
                                username.setText(name);
                                contact.setText(number);
                                nextkin.setText(nextofkin);
                                nextkincontact.setText(contactnextofkim);
                                address.setText(addresss);

                                addmanager.setVisibility(View.GONE);
                                editmanager.setVisibility(View.VISIBLE);
                                cardEdit.setVisibility(View.VISIBLE);


                            }
                        }
                    } catch (JSONException e) {
                        loadingUI.dismiss();
                        e.printStackTrace();
                        error_message_balance.setVisibility(View.VISIBLE);
//
                        addmanager.setVisibility(View.GONE);
                        editmanager.setVisibility(View.GONE);
//                         Toast.makeText(this, "Something went wrong, please try again"+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            loadingUI.dismiss();
            error_message_balance.setVisibility(View.VISIBLE);
            error_message_balance.setText(error.toString());
            addmanager.setVisibility(View.GONE);
            editmanager.setVisibility(View.GONE);
//            Toast.makeText(this, "Something went wrong, check your connection and try again please try again" + , Toast.LENGTH_SHORT).show();

        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("role", rolle);
                params.put("bossid", getID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void updateManager() {
        loadingUI = new Dialog(this);
        loadingUI.setContentView(R.layout.right);
        animationView = loadingUI.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView
                .playAnimation();
        loadingUI.show();

        String aa = address.getText().toString().trim();
        String uu = username.getText().toString().trim();
        String cc = contact.getText().toString().trim();
        String nx = nextkin.getText().toString().trim();
        String nxc = nextkincontact.getText().toString().trim();
        String idds = idd.getText().toString();
        String ages = age.getText().toString().trim();
        String genders = genderss.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.UPDATE_PROFILE,
                response -> {
                    try {

                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            loadingUI.dismiss();
                            Toast.makeText(getApplicationContext(), "Manager profile updated successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ManagerProfile.this,Employees.class));
                            finish();
                        }

                        loadingUI.setCancelable(false);
                        loadingUI.setCanceledOnTouchOutside(false);
                        Objects.requireNonNull(loadingUI.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        loadingUI.show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        loadingUI.dismiss();
                        Toast.makeText(getApplicationContext(), "Manager profile was not updated , please try again " + e.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    loadingUI.dismiss();
                    Toast.makeText(getApplicationContext(), "Manager profile was not updated , please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("address", aa);
                params.put("name", uu);
                params.put("contact", cc);
                params.put("nextkin", nx);
                params.put("nextkincontact", nxc);
                params.put("age", ages);
                params.put("role", "Manager");
                params.put("id", idds);
                params.put("gender", genders);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void insertManager() {
        cardAdd.setVisibility(View.GONE);
        loadingUI = new Dialog(this);
        loadingUI.setContentView(R.layout.right);
        animationView = loadingUI.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView
                .playAnimation();
        loadingUI.show();
        String aa = address.getText().toString().trim();
        String uu = username.getText().toString().trim();
        String cc = contact.getText().toString().trim();
        String nx = nextkin.getText().toString().trim();
        String nxc = nextkincontact.getText().toString().trim();
        String ages = age.getText().toString().trim();
        String genders = String.valueOf(gender.getSelectedItem());
//        urls.INSERT_PROFILE
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://jehooshfamily.com/myfarm/set_manager_profile.php",
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            loadingUI.dismiss();
                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ManagerProfile.this,Employees.class));
                            finish();
                        }
                        loadingUI.setCancelable(false);
                        loadingUI.setCanceledOnTouchOutside(false);
                        Objects.requireNonNull(loadingUI.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        loadingUI.dismiss();
                        cardAdd.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Registration was unsuccessful, please try again", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    loadingUI.dismiss();
                    cardAdd.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Registration continues to fail, please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("farmname", farmname);
                params.put("address", aa);
                params.put("name", uu);
                params.put("contact", cc);
                params.put("nextkin", nx);
                params.put("nextkincontact", nxc);
                params.put("role", "Manager");
                params.put("bossid", getID);
                params.put("gender", genders);
                params.put("age", ages);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void addManager(View view) {
        String aa = address.getText().toString().trim();
        String uu = username.getText().toString().trim();
        String cc = contact.getText().toString().trim();
        String nx = nextkin.getText().toString().trim();
        String nxc = nextkincontact.getText().toString().trim();
        String ages = age.getText().toString().trim();
        String genders = String.valueOf(gender.getSelectedItem());

        if (aa.isEmpty() && uu.isEmpty() && cc.isEmpty() && nx.isEmpty() && nxc.isEmpty() && ages.isEmpty() && genders.equals("Select gender")) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else {

            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    ManagerProfile.this);
            alertDialog2.setTitle("Confirm to update");
            alertDialog2.setMessage("Make sure you double check your details");
            alertDialog2.setPositiveButton("YES",
                    (dialog, which) -> {
                        insertManager();
                    });
            alertDialog2.setNegativeButton("NO",
                    (dialog, which) -> dialog.cancel());
            alertDialog2.show();

//            Toast.makeText(this, "ssss", Toast.LENGTH_SHORT).show();

        }
    }

    public void updateManager(View view) {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                ManagerProfile.this);
        alertDialog2.setTitle("Confirm to proceed to register");
        alertDialog2.setMessage("Make sure you double check your details");
        alertDialog2.setPositiveButton("YES",
                (dialog, which) -> {
                    updateManager();
                });
        alertDialog2.setNegativeButton("NO",
                (dialog, which) -> dialog.cancel());
        alertDialog2.show();
    }
    @Override
    public void onBackPressed() {
        return;
    }
}