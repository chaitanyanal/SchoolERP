package com.mipl.schoolerp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipl.schoolerp.Model.StaffBirthdayModule;
import com.mipl.schoolerp.R;

import java.util.ArrayList;

public class StaffBirthdayAdapter extends RecyclerView.Adapter<StaffBirthdayAdapter.MyViewHolder> {

    Context context;
    ArrayList<StaffBirthdayModule> birthdayModuleArrayList;

    public StaffBirthdayAdapter(Context context, ArrayList<StaffBirthdayModule> birthdayModuleArrayList) {
        this.context = context;
        this.birthdayModuleArrayList = birthdayModuleArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.birthday_list_data, viewGroup, false);
        return new StaffBirthdayAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        StaffBirthdayModule staffBirthdayModule=birthdayModuleArrayList.get(i);

        myViewHolder.Name.setText(staffBirthdayModule.getFullname());
        myViewHolder.DOB.setText(staffBirthdayModule.getDOB());

    }

    @Override
    public int getItemCount() {
        return birthdayModuleArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Name,DOB;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name=itemView.findViewById(R.id.Sname);
            DOB=itemView.findViewById(R.id.SDob);
        }
    }
}
