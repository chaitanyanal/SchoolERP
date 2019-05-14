package com.mipl.schoolerp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipl.schoolerp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnnualPlannerDetailsPage extends Fragment {

    TextView event_title,start_date,end_date,standerds_list,discription;


    public AnnualPlannerDetailsPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_annual_planner_details_page, container, false);

        event_title=view.findViewById(R.id.event_title);
        start_date=view.findViewById(R.id.start_date);
        end_date=view.findViewById(R.id.end_date);
        standerds_list=view.findViewById(R.id.standerds_list);
        discription=view.findViewById(R.id.discription);


        Bundle b = this.getArguments();
        String title=b.getString("title");
        String sdate=b.getString("start_date");
        String edate=b.getString("end_date");
        String standerds=b.getString("standerds");
        String remark=b.getString("remark");


        event_title.setText(title);
        start_date.setText(sdate);
        end_date.setText(edate);
        standerds_list.setText(standerds);

        if (remark!="null"){
            discription.setText(remark);
        }else {
            discription.setText("");
        }


        return view;
    }

}
