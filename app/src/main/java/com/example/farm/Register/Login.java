package com.example.farm.Register;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login extends AppCompatActivity {
    EditText username,password;
    Urls urls;
    SessionManager sessionManager;
    Dialog loadingUI;
    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingUI = new Dialog(this);
        setContentView(R.layout.activity_login);
        password = findViewById(R.id.password);
        sessionManager = new SessionManager(this);
        urls = new Urls();
    }

    public void openRegister(View view) {
        startActivity(new Intent(Login.this,Register.class));
    }

    public void openMain(View view) {
        String pass = password.getText().toString().trim();

        if (!pass.isEmpty()) {
            Logins(pass);

        } else {
            password.setError("Enter phone number");
        }
    }


    private void Logins(final String password) {

        loadingUI = new Dialog(this);
        loadingUI.setContentView(R.layout.right);
        animationView = loadingUI.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView
                .playAnimation();
        loadingUI.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOGIN_URL,
                response -> {
                    Log.i("tagconvertstr", "[" + response + "]");
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        @SuppressLint("NotConstructor") String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("login");
                        if (success.equals("1")) {
                            loadingUI.dismiss();
                            Toast.makeText(Login.this, "login success", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.i("tagconvertstr", "[" + response + "]");
                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("userid");
                                String fullname = object.getString("fullnames");
                                String password1 = object.getString("phone");
                                String status = object.getString("status");
                                String address = object.getString("address");
                                String farmname = object.getString("farmname");

                                sessionManager.createSession( password1,fullname, id,status,address,farmname);
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("username", fullname);
                                    startActivity(intent);
                                    finish();

                                }
                            loadingUI.setCancelable(false);
                            loadingUI.setCanceledOnTouchOutside(false);
                            Objects.requireNonNull(loadingUI.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                            loadingUI.show();

                        } else if (success.equals("0")) {
                            loadingUI.dismiss();
//                            login.setVisibility(View.VISIBLE);
                            Toast.makeText(Login.this, "login was error account not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        loadingUI.dismiss();
//                        login.setVisibility(View.VISIBLE);
                        Toast.makeText(Login.this, "failed to login, please try again ", Toast.LENGTH_LONG).show();
                    }
                },

                error -> {
                    loadingUI.dismiss();
//                    login.setVisibility(View.VISIBLE);
                    Toast.makeText(Login.this, "login error please check your internet connection and try again ", Toast.LENGTH_SHORT).show();
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}