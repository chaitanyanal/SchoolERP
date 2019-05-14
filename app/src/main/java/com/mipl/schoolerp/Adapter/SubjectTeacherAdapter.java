package com.mipl.schoolerp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipl.schoolerp.Model.TeacherListModel;
import com.mipl.schoolerp.R;

import java.util.ArrayList;

public class SubjectTeacherAdapter extends RecyclerView.Adapter<SubjectTeacherAdapter.MyViewHolder> {

    Context context;
    ArrayList<TeacherListModel> modelArrayList;

    public SubjectTeacherAdapter(Context context, ArrayList<TeacherListModel> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subject_teacher_list, viewGroup, false);
        return new SubjectTeacherAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        TeacherListModel teacherListModel=modelArrayList.get(i);

        myViewHolder.subjectName.setText(teacherListModel.getSubjectName());
        myViewHolder.subjectTeacherName.setText(teacherListModel.getTeachername());

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView subjectName,subjectTeacherName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            subjectName=itemView.findViewById(R.id.subjectName);
            subjectTeacherName=itemView.findViewById(R.id.subjectTeacherName);
        }
    }
}
