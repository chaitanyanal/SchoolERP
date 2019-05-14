package com.mipl.schoolerp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mipl.schoolerp.Model.AttendanceModuleModel;
import com.mipl.schoolerp.R;

import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {

    Context context;
    ArrayList<AttendanceModuleModel> modelArrayList;


    public AttendanceAdapter(Context context, ArrayList<AttendanceModuleModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attendance_list_data, viewGroup, false);
        return new AttendanceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        AttendanceModuleModel moduleModel=modelArrayList.get(i);

        myViewHolder.rollno.setText(moduleModel.getRollNo());
        myViewHolder.studname.setText(moduleModel.getStudentName());

        if (modelArrayList.get(i).getPresentAbsent().equalsIgnoreCase("false")){
            myViewHolder.rollno.setTextColor(Color.RED);
            myViewHolder.studname.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView rollno,studname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rollno=itemView.findViewById(R.id.rollNo);
            studname=itemView.findViewById(R.id.studentName);
        }
    }
}
