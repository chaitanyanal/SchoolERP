package com.mipl.schoolerp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipl.schoolerp.Model.MissingAttendanceModel;
import com.mipl.schoolerp.R;

import java.util.ArrayList;

public class MissingAttendanceAdapter extends RecyclerView.Adapter<MissingAttendanceAdapter.MyViewHolder> {

    Context context;
    ArrayList<MissingAttendanceModel> attendanceModelArrayList;

    public MissingAttendanceAdapter(Context context, ArrayList<MissingAttendanceModel> attendanceModelArrayList) {
        this.context = context;
        this.attendanceModelArrayList = attendanceModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.textview, viewGroup, false);
        return new MissingAttendanceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        MissingAttendanceModel missingAttendanceModel = attendanceModelArrayList.get(i);

        myViewHolder.txtName.setText(missingAttendanceModel.getName());

    }

    @Override
    public int getItemCount() {
        return attendanceModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName=itemView.findViewById(R.id.txtview);
        }
    }
}
