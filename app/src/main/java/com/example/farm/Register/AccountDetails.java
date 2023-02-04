package com.example.farm.Register;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farm.MainActivity;
import com.example.farm.R;
import com.example.farm.Urls.SessionManager;
import com.example.farm.Urls.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccountDetails extends AppCompatActivity {

    EditText username, password;
    SessionManager sessionManager;
    Urls urls;
    String getID, getName, getPhone;
    TextView userid;
    LottieAnimationView animationView;
    Dialog loadingUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountdetails);
        loadingUI = new Dialog(this);
        urls = new Urls();
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        getName = user.get(SessionManager.FULLNAME);
        getPhone = user.get(SessionManager.CONTACT);
        getID = user.get(SessionManager.ID);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        userid = findViewById(R.id.userid);

        username.setText(getName);
        password.setText(getPhone);
        userid.setText(getID);
//        animationView = findViewById(R.id.animationView);
    }


    public void update(View view) {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                AccountDetails.this);
        alertDialog2.setTitle("Confirm to update profile");
        alertDialog2.setMessage("Make sure you double check your details");
        alertDialog2.setPositiveButton("YES",
                (dialog, which) -> {
                    update();
                });
        alertDialog2.setNegativeButton("NO",
                (dialog, which) -> dialog.cancel());
        alertDialog2.show();

    }

    private void update() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait ...");
        dialog.show();

        final String full_names = this.username.getText().toString().trim();
        final String pass = this.password.getText().toString().trim();
        String userids = userid.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.UPDATE_USER_PROFILE_URL,
                response -> {
                    try {

                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();

                            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                                    AccountDetails.this);
                            alertDialog2.setTitle("Profile updated successfully");
                            alertDialog2.setMessage("You are about to be logged out, please log in again with your logged profile");
                            alertDialog2.setPositiveButton("YES",
                                    (dialogs, which) -> {
                                        sessionManager.logout();
                                    });
                            alertDialog2.show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Profile was not updated unsuccessfully, please try again", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Profile was not updated unsuccessfully, please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fullname", full_names);
                params.put("password", pass);
                params.put("userid", userids);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void goBack(View view) {
        startActivity(new Intent(AccountDetails.this, MainActivity.class));
        finish();
    }




}