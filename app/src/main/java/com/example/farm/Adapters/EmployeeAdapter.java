package com.example.farm.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farm.Modals.EmployeeModel;
import com.example.farm.Modals.MatookeModel;
import com.example.farm.R;
import com.example.farm.Urls.Urls;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MilkViewHolder> {
    Context context;
    public static  List<EmployeeModel> mData;
    Urls urls;

    public EmployeeAdapter(Context context, List<EmployeeModel> mData) {
        this.context = context;
        this.mData = mData;
        urls = new Urls();
    }


    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public MilkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_employess, null, false);
        return new MilkViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MilkViewHolder holder, final int position) {

        EmployeeModel authorModel = mData.get(position);
        holder.name.setText(mData.get(position).getName());
        holder.id.setText(mData.get(position).getId());
        holder.number.setText(mData.get(position).getNumber());
        holder.role.setText(mData.get(position).getRole());
        holder.address.setText(mData.get(position).getAddress());
        holder.gender.setText(mData.get(position).getGender());
        holder.nextofkin.setText(mData.get(position).getNextofkin());
        holder.contactnextofkim.setText(mData.get(position).getContactnextofkim());

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

        TextView name,number,role,address,gender,id,nextofkin,contactnextofkim;

        public MilkViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.names);
            number = itemView.findViewById(R.id.phone);
            role = itemView.findViewById(R.id.role);
            address = itemView.findViewById(R.id.address);
            gender = itemView.findViewById(R.id.gender);
            id = itemView.findViewById(R.id.id);
            nextofkin = itemView.findViewById(R.id.nextofkin);
            contactnextofkim = itemView.findViewById(R.id.contactnextofkim);

        }
    }
}
