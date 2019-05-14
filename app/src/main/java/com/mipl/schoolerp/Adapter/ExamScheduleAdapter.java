package com.mipl.schoolerp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipl.schoolerp.Model.ExamScheduleModel;
import com.mipl.schoolerp.R;

import java.util.ArrayList;

public class ExamScheduleAdapter extends RecyclerView.Adapter<ExamScheduleAdapter.MyViewHolder> {

    Context context;
    ArrayList<ExamScheduleModel> scheduleModelArrayList;


    public ExamScheduleAdapter(Context context, ArrayList<ExamScheduleModel> scheduleModelArrayList) {
        this.context = context;
        this.scheduleModelArrayList = scheduleModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exam_schedule_list_data, viewGroup, false);
        return new ExamScheduleAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        ExamScheduleModel examScheduleModel=scheduleModelArrayList.get(i);

        myViewHolder.ExamName.setText(examScheduleModel.getSubjectName());
        myViewHolder.ExamDate.setText(examScheduleModel.getExamDate());
        myViewHolder.Description.setText(examScheduleModel.getDescription());

    }

    @Override
    public int getItemCount() {
        return scheduleModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView ExamName,ExamDate,Description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ExamName=itemView.findViewById(R.id.SubjectName);
            ExamDate=itemView.findViewById(R.id.ExamDate);
            Description=itemView.findViewById(R.id.Description);

        }
    }
}
