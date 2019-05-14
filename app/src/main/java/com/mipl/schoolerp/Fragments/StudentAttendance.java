package com.mipl.schoolerp.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.HolidayAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.HolidayModel;
import com.mipl.schoolerp.Model.StudentAttendanceModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

public class StudentAttendance extends Fragment{

    Spinner monthNames;
    TextView workinkDays,presentDays,totalDays;

    ArrayList<String> monthList;

    ArrayList<StudentAttendanceModel> attendanceModelArrayList;

    ProgressDialog dialog;


    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String UserId, name, role, LoginId, id;


    public StudentAttendance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_student_attendance, container, false);



        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();

        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");



        monthNames=view.findViewById(R.id.monthNames);
        workinkDays=view.findViewById(R.id.workinkDays);
        presentDays=view.findViewById(R.id.presentDays);
        totalDays=view.findViewById(R.id.totalDays);


        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();

        getStudentData();


        monthList=new ArrayList<>();
        attendanceModelArrayList=new ArrayList<>();


        monthList.add("Select Month");
        monthList.add("January");
        monthList.add("February");
        monthList.add("March");
        monthList.add("April");
        monthList.add("May");
        monthList.add("June");
        monthList.add("July");
        monthList.add("August");
        monthList.add("September");
        monthList.add("October");
        monthList.add("November");
        monthList.add("December");



        ArrayAdapter aa = new ArrayAdapter(getContext(),R.layout.text_layout,monthList);
        aa.setDropDownViewResource(R.layout.text_layout);
        monthNames.setAdapter(aa);


        monthNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    String aaa = parent.getSelectedItem().toString();

                    if (!aaa.equalsIgnoreCase("Select Month")){

                        for (int i=0;i<attendanceModelArrayList.size();i++){
                            if (aaa.equalsIgnoreCase(attendanceModelArrayList.get(i).getMonth())){

                                workinkDays.setText(attendanceModelArrayList.get(i).getTotalWorkingDays());
                                presentDays.setText(attendanceModelArrayList.get(i).getPreentDays());
                                totalDays.setText(attendanceModelArrayList.get(i).getAbsentDays());
                                break;
                            }else {
                                workinkDays.setText("");
                                presentDays.setText("");
                                totalDays.setText("");
                            }
                        }

                        Log.d("aaa",aaa);
                    }else {
                        workinkDays.setText("");
                        presentDays.setText("");
                        totalDays.setText("");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    private void getStudentData() {

        Map<String,String> params=new HashMap<>();
        params.put("UserId",id);


        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.MonthWisePresentAbsentData, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Log.d("res", response.toString());

                try {
                    dialog.dismiss();
                    dialog.hide();


                    JSONArray jsonArray = response.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String PresentDays = jsonObject.getString("PresentDays");
                        String AbsentDays = jsonObject.getString("AbsentDays");
                        String Month = jsonObject.getString("Month");
                        String StudentId = jsonObject.getString("StudentId");
                        String totalWorkingDays = jsonObject.getString("totalWorkingDays");

                        StudentAttendanceModel studentAttendanceModel=new StudentAttendanceModel(PresentDays,AbsentDays,Month,StudentId,totalWorkingDays);
                        attendanceModelArrayList.add(studentAttendanceModel);
                    }

                    if (attendanceModelArrayList.size()==0){

                        dialog.dismiss();
                        dialog.hide();

                        Toast.makeText(getContext(), "No Data Available...", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    dialog.dismiss();
                    dialog.hide();

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();
                dialog.hide();

                Toast.makeText(getContext(), "Server Error...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }


}
