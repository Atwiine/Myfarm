package com.example.farm.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farm.Modals.MatookeModel;
import com.example.farm.R;
import com.example.farm.Urls.Urls;

import java.util.List;

public class MatookAdapter extends RecyclerView.Adapter<MatookAdapter.MilkViewHolder> {
    Context context;
    public static  List<MatookeModel> mData;
    Urls urls;

    public MatookAdapter(Context context, List<MatookeModel> mData) {
        this.context = context;
        this.mData = mData;
        urls = new Urls();
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public MilkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_matooke, null, false);
        return new MilkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MilkViewHolder holder, final int position) {

        MatookeModel authorModel = mData.get(position);
        holder.total_bunches.setText(mData.get(position).getNumberBanches());
        holder.id.setText(mData.get(position).getId());
        holder.date.setText(mData.get(position).getDate());


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

        TextView total_bunches, id,date;

        public MilkViewHolder(@NonNull View itemView) {
            super(itemView);

            total_bunches = itemView.findViewById(R.id.total_bunches);
            date = itemView.findViewById(R.id.date);
            id = itemView.findViewById(R.id.id);

        }
    }
}
