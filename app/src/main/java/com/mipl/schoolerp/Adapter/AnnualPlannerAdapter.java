package com.mipl.schoolerp.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipl.schoolerp.Fragments.AnnualPlanner;
import com.mipl.schoolerp.Fragments.AnnualPlannerDetailsPage;
import com.mipl.schoolerp.Model.AnnualPlannerModel;
import com.mipl.schoolerp.R;
import java.util.ArrayList;

public class AnnualPlannerAdapter extends RecyclerView.Adapter<AnnualPlannerAdapter.MyViewHolder> {

    Context context;
    ArrayList<AnnualPlannerModel> plannerModelArrayList;

    public AnnualPlannerAdapter(Context context, ArrayList<AnnualPlannerModel> plannerModelArrayList) {
        this.context = context;
        this.plannerModelArrayList = plannerModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.annual_planner_list_item, viewGroup, false);
        return new AnnualPlannerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        AnnualPlannerModel annualPlannerModel=plannerModelArrayList.get(i);

        myViewHolder.day.setText(annualPlannerModel.getStartdate());
        myViewHolder.event_title.setText(annualPlannerModel.getName());
        myViewHolder.standards.setText(annualPlannerModel.getStandard());

        myViewHolder.event_title.setPaintFlags(myViewHolder.event_title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG );


    }

    @Override
    public int getItemCount() {
        return plannerModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView day,event_title,standards;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            day=itemView.findViewById(R.id.day);
            event_title=itemView.findViewById(R.id.event_title);
            standards=itemView.findViewById(R.id.standards);



            event_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i=getAdapterPosition();

                    Bundle bundle=new Bundle();

                    bundle.putString("title",plannerModelArrayList.get(i).getName());
                    bundle.putString("start_date",plannerModelArrayList.get(i).getStartdate());
                    bundle.putString("end_date",plannerModelArrayList.get(i).getEndDate());
                    bundle.putString("standerds",plannerModelArrayList.get(i).getStandard());
                    bundle.putString("remark",plannerModelArrayList.get(i).getRemark());


                    Fragment fragment = new AnnualPlannerDetailsPage();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }
    }
}
