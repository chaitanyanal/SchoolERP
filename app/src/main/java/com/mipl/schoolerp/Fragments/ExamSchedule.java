package com.mipl.schoolerp.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.AnnualPlannerAdapter;
import com.mipl.schoolerp.Adapter.ExamScheduleAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.AnnualPlannerModel;
import com.mipl.schoolerp.Model.ExamScheduleModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

public class ExamSchedule extends Fragment {

    Spinner ExamName;

    RecyclerView recyclerView;

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String UserId, name, role, LoginId, id;


    ArrayList<ExamScheduleModel> scheduleModelArrayList;
    ArrayList<ExamScheduleModel> tempList;

    ArrayList<String> classNamelist;


    public ExamSchedule() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_exam_schedule, container, false);


        ExamName=view.findViewById(R.id.examName);
        recyclerView=view.findViewById(R.id.recyclerview);

        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();

        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");

        scheduleModelArrayList=new ArrayList<>();

        classNamelist=new ArrayList<>();
        classNamelist.add(0,"Select Exam");

        getExamNameList();

        ExamName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tempList=new ArrayList<>();

                String name=parent.getSelectedItem().toString();

                for (int i=0;i<scheduleModelArrayList.size();i++){
                    if (name.equalsIgnoreCase(scheduleModelArrayList.get(i).getExamName())){

                        String Eid=scheduleModelArrayList.get(i).getExamId();
                        String Ename=scheduleModelArrayList.get(i).getExamName();
                        String Edate=scheduleModelArrayList.get(i).getExamDate();
                        String Edesc=scheduleModelArrayList.get(i).getDescription();
                        String Esunname=scheduleModelArrayList.get(i).getSubjectName();

                        ExamScheduleModel examScheduleModel=new ExamScheduleModel(Eid,Ename,Esunname,Edate,Edesc);
                        tempList.add(examScheduleModel);
                    }
                }

                if (tempList.size() > 0) {
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(manager);
                    ExamScheduleAdapter adapter1 = new ExamScheduleAdapter(getContext(),tempList);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adapter1);
                }else {
                    recyclerView.setAdapter(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    private void getExamNameList() {

        Map<String,String> params=new HashMap<>();
        params.put("UserId",id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.ExamNameList, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());

                try {

                    JSONArray jsonArray = response.getJSONArray("ExamList");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String examId = jsonObject.getString("examId");
                        String ExamName = jsonObject.getString("ExamName");
                        String SubjectName = jsonObject.getString("SubjectName");
                        String ExamDate = jsonObject.getString("ExamDate");
                        String Description = jsonObject.getString("Description");


                        ExamScheduleModel examScheduleModel=new ExamScheduleModel(examId,ExamName,SubjectName,ExamDate,Description);
                        scheduleModelArrayList.add(examScheduleModel);

                        classNamelist.add(ExamName);
                    }

                    ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, classNamelist);
                    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                    ExamName.setAdapter(adapter);


                    if (scheduleModelArrayList.size()==0){
                        Toast.makeText(getContext(), "No Data Available...", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Server Error...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);


    }


}
