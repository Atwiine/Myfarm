package com.example.farm.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farm.Modals.EmployeeModel;
import com.example.farm.Modals.MilkResultsModel;

import com.example.farm.R;
//import com.example.farm.Urls.SessionManager;
import com.example.farm.Urls.Urls;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MilkResultsAdapter extends RecyclerView.Adapter<MilkResultsAdapter.MilkViewHolder> implements Filterable {
    Context context;
    public static  List<MilkResultsModel> mData;
    Urls urls;
    List<MilkResultsModel> milk_filter;
    private final Filter examplefilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MilkResultsModel> filterexample = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterexample.addAll(milk_filter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (MilkResultsModel marketsModel : milk_filter) {
                    if (marketsModel.getTime().toLowerCase().contains(filterPattern)) {
                        filterexample.add(marketsModel);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterexample;
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mData.clear();
            mData.addAll((Collection<? extends MilkResultsModel>) results.values);
            notifyDataSetChanged();
        }
    };


    public MilkResultsAdapter(Context context, List<MilkResultsModel> mData) {
        this.context = context;
        this.mData = mData;
        urls = new Urls();
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public MilkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_milkresults, null, false);
        return new MilkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MilkViewHolder holder, final int position) {

        MilkResultsModel authorModel = mData.get(position);
        holder.home_litres.setText(mData.get(position).getHome());
        holder.diary_litres.setText(mData.get(position).getDiary());
        holder.id.setText(mData.get(position).getId());
        holder.date.setText(mData.get(position).getDate());
        holder.total_litres.setText(mData.get(position).getTotal());
        holder.comment.setText(mData.get(position).getComment());
        holder.timesent.setText(mData.get(position).getTime());


        /*check to see if there is a comment and show the comment section*/
        String cc = holder.comment.getText().toString();
        if (cc.isEmpty()){
            holder.linear_comment.setVisibility(View.GONE);
        }else {
            holder.linear_comment.setVisibility(View.VISIBLE);
        }

        //check which timeslot it is and then display the right signal
        String tt = holder.timesent.getText().toString();
        switch (tt) {
            case "Morning":
                holder.relmorning.setVisibility(View.VISIBLE);
                break;
            case "Afternoon":
                holder.relafternoon.setVisibility(View.VISIBLE);
                break;
            case "Evening":
                holder.relevening.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return examplefilter;
    }

    public void filterList(ArrayList<MilkResultsModel> filteredList) {
        mData = filteredList;
        notifyDataSetChanged();
//        Clear();
    }

    public void clear() {
        int size = mData.size();
        if (size > 0) {
            mData.subList(0, size).clear();

            notifyItemRangeRemoved(0, size);
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    public void Clear() {
        mData.clear();
       notifyDataSetChanged();
    }
    public static class MilkViewHolder extends RecyclerView.ViewHolder {

        TextView home_litres, diary_litres, id,date,total_litres,comment,timesent;
        LinearLayout linear_comment;
        RelativeLayout relevening,relafternoon,relmorning;

        public MilkViewHolder(@NonNull View itemView) {
            super(itemView);

            home_litres = itemView.findViewById(R.id.home_litres);
            diary_litres = itemView.findViewById(R.id.diary_litres);
            date = itemView.findViewById(R.id.date);
            id = itemView.findViewById(R.id.id);
            total_litres = itemView.findViewById(R.id.total_litres);
            comment = itemView.findViewById(R.id.comment);
            linear_comment = itemView.findViewById(R.id.linear_comment);
            relevening = itemView.findViewById(R.id.relevening);
            relafternoon = itemView.findViewById(R.id.relafternoon);
            relmorning = itemView.findViewById(R.id.relmorning);
            timesent = itemView.findViewById(R.id.timesent);

        }
    }
}
