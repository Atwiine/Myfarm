package com.example.farm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.farm.Urls.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class SettingsActivity extends BottomSheetDialogFragment {

    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {

        sessionManager = new SessionManager(getActivity());

        View v = inflater.inflate(R.layout.activity_settings,
                container, false);

        LinearLayout linear_logout = v.findViewById(R.id.linear_logout);

        linear_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        getActivity());
                alertDialog2.setTitle("Confirm to proceed");
                alertDialog2.setMessage("Are you sure you want to logout");
                alertDialog2.setPositiveButton("YES",
                        (dialog, which) -> sessionManager.logout());
                alertDialog2.setNegativeButton("NO",
                        (dialog, which) -> dialog.cancel());
                alertDialog2.show();
            }
        });

        return v;
    }
}