package com.example.wastify_codejod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyLeaveAdapter extends RecyclerView.Adapter<MyLeaveAdapter.MyViewHolder>{
    Context context;
    ArrayList<UserForLeave> uArrayList;

    public MyLeaveAdapter(Context context, ArrayList<UserForLeave> uArrayList) {
        this.context = context;
        this.uArrayList = uArrayList;
    }

    @NonNull
    @Override
    public MyLeaveAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_containing_leaves,parent,false);
        return new MyLeaveAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLeaveAdapter.MyViewHolder holder, int position) {
        UserForLeave userForLeave = uArrayList.get(position);
        holder.nameForLeave.setText(userForLeave.name_leave);
        holder.dateForLeave.setText(userForLeave.date_leave);
        holder.reasonForLeave.setText(userForLeave.leave_reason);
    }

    @Override
    public int getItemCount() {
        return uArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameForLeave,dateForLeave,reasonForLeave;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameForLeave = itemView.findViewById(R.id.tvDriverNameForLeave);
            dateForLeave=itemView.findViewById(R.id.tvDatesL);
            reasonForLeave=itemView.findViewById(R.id.tvReasonForLeave);
        }
}}
