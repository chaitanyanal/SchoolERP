package com.mipl.schoolerp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipl.schoolerp.Model.ParentTeacherModel;
import com.mipl.schoolerp.R;

import java.util.ArrayList;

public class ParentTeacherAssociationAdapter extends RecyclerView.Adapter<ParentTeacherAssociationAdapter.MyViewHolder> {

    Context context;
    ArrayList<ParentTeacherModel> arrayList;

    public ParentTeacherAssociationAdapter(Context context, ArrayList<ParentTeacherModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_layout, viewGroup, false);
        return new ParentTeacherAssociationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        ParentTeacherModel parentTeacherModel=arrayList.get(i);

        myViewHolder.textView.setText(parentTeacherModel.getName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView =itemView.findViewById(R.id.text);
        }
    }
}
