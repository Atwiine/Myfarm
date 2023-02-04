package com.example.farm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.farm.Register.AccountDetails;
import com.example.farm.Urls.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;

import java.util.HashMap;


public class SettingsActivity extends BottomSheetDialogFragment {

    SessionManager sessionManager;
    String getName, getPhone, dd;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {

        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getUserDetail();
        getName = user.get(SessionManager.FULLNAME);
        getPhone = user.get(SessionManager.CONTACT);

        View v = inflater.inflate(R.layout.activity_settings,
                container, false);

        LinearLayout linear_logout = v.findViewById(R.id.linear_logout);
        RelativeLayout card_account = v.findViewById(R.id.editprofile);
        TextView names = v.findViewById(R.id.names);
        TextView contact = v.findViewById(R.id.contact);

        names.setText(getName);
        contact.setText(getPhone);

        card_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AccountDetails.class));
            }
        });

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