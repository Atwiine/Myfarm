package com.example.farm.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farm.Modals.AnimalResultsModel;
import com.example.farm.Modals.MilkResultsModel;
import com.example.farm.R;
import com.example.farm.Urls.Urls;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.MaterialCalendar;

import java.util.List;

public class AnimalResultsAdapter extends RecyclerView.Adapter<AnimalResultsAdapter.MilkViewHolder> {
    Context context;
    public static  List<AnimalResultsModel> mData;
    Urls urls;
//    SessionManager sessionManager;
    String  getTYPE;
    String getId, idPost, teacher;

    public AnimalResultsAdapter(Context context, List<AnimalResultsModel> mData) {
        this.context = context;
        this.mData = mData;
        urls = new Urls();
//        sessionManager = new SessionManager(context);
//        HashMap<String, String> user = sessionManager.getUserDetail();
//        getId = user.get(SessionManager.ID);
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public MilkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_animalresults, null, false);
        return new MilkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MilkViewHolder holder, final int position) {

        AnimalResultsModel authorModel = mData.get(position);
        holder.tagnumber.setText(mData.get(position).getTagnumber());
        holder.gender.setText(mData.get(position).getGender());
        holder.id.setText(mData.get(position).getId());
        holder.date.setText(mData.get(position).getDate());
        holder.weight.setText(mData.get(position).getWeight());
        holder.checker.setText(mData.get(position).getChecker());
        holder.ptagnumber.setText(mData.get(position).getParent_tagnumber());

//use the checker to see if the cow has any new borns attached to it
        String cc = holder.checker.getText().toString();
        if (cc.equals("Yes")){
            holder.linear_parent.setVisibility(View.VISIBLE);
        }else {
            holder.linear_parent.setVisibility(View.GONE);
        }

        //handle the showing of the new borns
        holder.cardnewborn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "coming soon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void clear() {
        int size = mData.size();
        if (size > 0) {
            mData.subList(0, size).clear();

            notifyItemRangeRemoved(0, size);
        }
    }
    public static class MilkViewHolder extends RecyclerView.ViewHolder {

        TextView tagnumber, gender, id,date,weight,checker,ptagnumber;
        LinearLayout linear_parent;
        MaterialCardView cardnewborn;

        public MilkViewHolder(@NonNull View itemView) {
            super(itemView);

            tagnumber = itemView.findViewById(R.id.tagnumber);
            gender = itemView.findViewById(R.id.gender);
            date = itemView.findViewById(R.id.date);
            id = itemView.findViewById(R.id.id);
            weight = itemView.findViewById(R.id.weight);
            checker = itemView.findViewById(R.id.checker);
            cardnewborn = itemView.findViewById(R.id.cardnewborn);
            linear_parent = itemView.findViewById(R.id.linear_parent);
            ptagnumber = itemView.findViewById(R.id.ptagnumber);

        }
    }
}
