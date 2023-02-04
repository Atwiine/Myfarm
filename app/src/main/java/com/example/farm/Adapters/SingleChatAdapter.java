package com.example.farm.Adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farm.Modals.ChatModel;
import com.example.farm.R;
import com.example.farm.Urls.SessionManager;
import com.example.farm.Urls.Urls;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SingleChatAdapter extends RecyclerView.Adapter<SingleChatAdapter.ChatViewHolder> {

    Context context;
    List<ChatModel> mData;
    SessionManager sessionManager;
    String getId, session_contact, contact;
    Urls urls;

    public SingleChatAdapter(Context context, List<ChatModel> mData) {
        this.context = context;
        this.mData = mData;
        urls = new Urls();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_chat, null, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, final int position) {
        //        handle session manager

//        notifyItemRangeChanged(position);

//        notifyItemChanged(position);
//        notifyItemInserted(position);
//        notifyItemRemoved(position);

        sessionManager = new SessionManager(context);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        contact = user.get(SessionManager.FULLNAME);
        holder.session_no.setText(contact);

        ChatModel chatModel = mData.get(position);
//        holder.message.setText(mData.get(position).getMessage());
        holder.sender_no.setText(mData.get(position).getSender());
        holder.receiver_no.setText(mData.get(position).getReceiver());


        if (chatModel.getRecieverID().equals(getId)) {

            holder.card_sender.setVisibility(View.VISIBLE);
            holder.card_receiver.setVisibility(View.GONE);
            holder.sender_message.setText(mData.get(position).getMessage());
            holder.sender_time.setText(mData.get(position).getTime());

            /*for loading sender images*/

            try {

                Glide.with(context)
                        .load(R.drawable.logo1)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .fallback(R.drawable.ic_launcher_background)
                        .into(holder.sender_image);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {

            holder.card_receiver.setVisibility(View.VISIBLE);
            holder.card_sender.setVisibility(View.GONE);
            holder.receiver_message.setText(mData.get(position).getMessage()); ///gets message
            holder.receiver_time.setText(mData.get(position).getTime()); ///gets time
            try {

                Glide.with(context)
                        .load(R.drawable.logo1)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .fallback(R.drawable.ic_launcher_background)
                        .into(holder.receiver_image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();

    }

    public void resetData(ArrayList<ChatModel> programsList) {
        this.mData = programsList;
//        this.notifyDataSetChanged();
//        notifyItemChanged(yourPosition);
//        notifyItemInserted(yourPosition);
//        notifyItemRemoved(yourPosition);
    }
    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView receiver_message, receiver_time, session_no, sender_no, receiver_no, sender_message, sender_time;
        RelativeLayout card_sender, fcard_sms_farmer, card_receiver;
        ImageView sender_image, receiver_image;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            receiver_message = itemView.findViewById(R.id.receiver_message);
            receiver_time = itemView.findViewById(R.id.receiver_time);
            session_no = itemView.findViewById(R.id.session_no);
            sender_no = itemView.findViewById(R.id.sender_no);
            receiver_no = itemView.findViewById(R.id.receiver_no);
            card_sender = itemView.findViewById(R.id.card_sender);
            card_receiver = itemView.findViewById(R.id.card_receiver);
            sender_message = itemView.findViewById(R.id.sender_message);
            sender_time = itemView.findViewById(R.id.sender_time);
            sender_image = itemView.findViewById(R.id.sender_image);
            receiver_image = itemView.findViewById(R.id.receiver_image);
        }

    }
}
