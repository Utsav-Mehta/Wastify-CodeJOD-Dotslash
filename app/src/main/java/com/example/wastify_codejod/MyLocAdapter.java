package com.example.wastify_codejod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyLocAdapter extends RecyclerView.Adapter<MyLocAdapter.MyViewHolder>{
    Context context;
    ArrayList<UserForLoc> userArrayList;

    public MyLocAdapter(Context context, ArrayList<UserForLoc> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyLocAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_containing_location,parent,false);
        return new MyLocAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLocAdapter.MyViewHolder holder, int position) {
        UserForLoc userForLoc = userArrayList.get(position);
        holder.longitude.setText(String.valueOf(userForLoc.longitude));
        holder.latitude.setText(String.valueOf(userForLoc.latitude));
        holder.addressLine.setText(userForLoc.address_line);
        holder.date.setText(userForLoc.date);
        holder.time.setText(userForLoc.time);
        holder.driver_name.setText(userForLoc.driver_name);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView longitude, latitude,addressLine,date,time,locality,driver_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            longitude = itemView.findViewById(R.id.tvLong);
            latitude = itemView.findViewById(R.id.tvLat);
            addressLine = itemView.findViewById(R.id.tvAddress);
            date= itemView.findViewById(R.id.tvDate);
            time = itemView.findViewById(R.id.tvTime);
            locality = itemView.findViewById(R.id.tvLocality);
            driver_name=itemView.findViewById(R.id.tvDriverName);
        }
    }
}
