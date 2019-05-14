package com.mipl.schoolerp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipl.schoolerp.Model.HolidayModel;
import com.mipl.schoolerp.R;

import java.util.ArrayList;

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.MyViewHolder> {

    Context context;
    ArrayList<HolidayModel> holidayModelList;

    public HolidayAdapter(Context context, ArrayList<HolidayModel> holidayModelList) {
        this.context = context;
        this.holidayModelList = holidayModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.holiday_list_data, viewGroup, false);
        return new HolidayAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        HolidayModel holidayModel=holidayModelList.get(i);
        myViewHolder.Name.setText(holidayModel.getName());
        myViewHolder.StartDate.setText(holidayModel.getStartDate());
        myViewHolder.EndDate.setText(holidayModel.getEndDate());
    }

    @Override
    public int getItemCount() {
        return holidayModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView Name,StartDate,EndDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name=itemView.findViewById(R.id.Name);
            StartDate=itemView.findViewById(R.id.StartDate);
            EndDate=itemView.findViewById(R.id.EndDate);
        }
    }
}
