package com.example.wastify_codejod;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Users> usersArrayList;

    public MyAdapter(Context context, ArrayList<Users> usersArrayList) {
        this.context = context;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_containing_complains,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
            Users users = usersArrayList.get(position);
            holder.name.setText(users.name_complain);
            holder.phone_comp.setText(users.phone_complain);
            holder.complain.setText(users.complain);
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, phone_comp,complain;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tvfirstname);
            phone_comp =itemView.findViewById(R.id.phoneCitizen);
            complain=itemView.findViewById(R.id.tvComplain);
        }
    }
}
