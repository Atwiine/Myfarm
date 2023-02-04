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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.farm.R;
import com.example.farm.Urls.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {

    EditText username,password,address,farmname;
    Urls urls;
    LottieAnimationView animationView;
    Dialog loadingUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loadingUI = new Dialog(this);
        urls = new Urls();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        address = findViewById(R.id.address);
        farmname = findViewById(R.id.farmname);
//        animationView = findViewById(R.id.animationView);
    }

    public void openLogin(View view) {
        startActivity(new Intent(Register.this, Login.class));
    }

    public void register(View view) {

        final String full_name_val = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        final String addresss = address.getText().toString().trim();
        String farmnamed = farmname.getText().toString().trim();


        if (!full_name_val.isEmpty()  && !pass.isEmpty() && !addresss.isEmpty()  && !farmnamed.isEmpty()
                   ) {

                    AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                            Register.this);
                    alertDialog2.setTitle("Confirm to proceed to register");
                    alertDialog2.setMessage("Make sure you double check your registration details");
                    alertDialog2.setPositiveButton("YES",
                            (dialog, which) -> {
                                Registers();
                            });
                    alertDialog2.setNegativeButton("NO",
                            (dialog, which) -> dialog.cancel());
                    alertDialog2.show();

                } else {
                    Toast.makeText(Register.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }


}



    private void Registers() {
//        final ProgressDialog dialog = new ProgressDialog(this);
//        dialog.setMessage("Please wait ...");
//        dialog.show();


        loadingUI = new Dialog(this);
        loadingUI.setContentView(R.layout.right);
        animationView = loadingUI.findViewById(R.id.animationView);
        animationView.setVisibility(View.VISIBLE);
        animationView
                .playAnimation();
        loadingUI.show();

        final String full_names = this.username.getText().toString().trim();
        final String pass = this.password.getText().toString().trim();
        final String addresss = address.getText().toString().trim();
        String farmnamed = farmname.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.FIRSTREG_URL,
                response -> {
                    try {

                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            loadingUI.dismiss();
                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();

                            Intent aa = new Intent(Register.this, Login.class);
                            startActivity(aa);
                            finish();

                        }if (success.equals("2")) {
                            Toast.makeText(getApplicationContext(),"Sorry account already taken, please choose another username and try again.",Toast.LENGTH_SHORT).show();
                            loadingUI.dismiss();
                        }

                        loadingUI.setCancelable(false);
                        loadingUI.setCanceledOnTouchOutside(false);
                        Objects.requireNonNull(loadingUI.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        loadingUI.show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        loadingUI.dismiss();
                        Toast.makeText(getApplicationContext(), "Registration was unsuccessful, please try again", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    loadingUI.dismiss();
                    Toast.makeText(getApplicationContext(), "Registration continues to fail, please check your internet connection and try again", Toast.LENGTH_SHORT).show();
                }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fullname", full_names);
                params.put("password", pass);
                params.put("farmname", farmnamed);
                params.put("address", addresss);
                params.put("status", "Boss");
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }






}