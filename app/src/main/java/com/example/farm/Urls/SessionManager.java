package com.example.farm.Urls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.farm.MainActivity;
import com.example.farm.Register.Login;

import java.util.HashMap;

public class SessionManager {
    public static final String CONTACT = "CONTACT";
    public static final String FULLNAME = "FULLNAME";
    public static final String STATUS = "STATUS";
    public static final String ADDRESS = "ADDRESS";
    public static final String FARMNAME = "FARMNAME";
    public static final String ID = "ID";
    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public SharedPreferences.Editor editor;
    public Context context;
    SharedPreferences sharedPreferences;
    int PRIVATE_MODE = 0;

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String contact, String fullname, String id,String status, String address, String farmname
    ) {
        editor.putBoolean(LOGIN, true);
        editor.putString(CONTACT, contact);
        editor.putString(FULLNAME, fullname);
        editor.putString(ID, id);
        editor.putString(STATUS, status);
        editor.putString(ADDRESS, address);
        editor.putString(FARMNAME, farmname);
        editor.apply();

    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {
        if (!this.isLogin()) {
            Intent i = new Intent(context, Login.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(CONTACT, sharedPreferences.getString(CONTACT, null));
        user.put(FULLNAME, sharedPreferences.getString(FULLNAME, null));
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(STATUS, sharedPreferences.getString(STATUS, null));
        user.put(ADDRESS, sharedPreferences.getString(ADDRESS, null));
        user.put(FARMNAME, sharedPreferences.getString(FARMNAME, null));

        return user;
    }

    public void logout() {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, Login.class);
        intent.putExtra("finish", true);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
