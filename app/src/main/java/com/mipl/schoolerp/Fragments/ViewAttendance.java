package com.mipl.schoolerp.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.AttendanceAdapter;
import com.mipl.schoolerp.Adapter.HolidayAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.AttendanceModuleModel;
import com.mipl.schoolerp.Model.AttendanceSubModuleModel;
import com.mipl.schoolerp.Model.ClassModule;
import com.mipl.schoolerp.Model.HolidayModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewAttendance extends Fragment {


    TextView currentDate,sunday;
    Button back, forword;
    LinearLayout llmain;

    ArrayList<String> classnamelist;
    ArrayList<ClassModule> classModulesList;

    ArrayList<AttendanceSubModuleModel> subModuleModelArrayList;
    ArrayList<AttendanceModuleModel> modelArrayList;

    ArrayList<AttendanceModuleModel> tempData;
    ArrayList<HolidayModel> holidayModelArrayList;

    Spinner className;
    RecyclerView recyclerView;

    String StanderdId, DivisionId;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Calendar c = Calendar.getInstance();

    String todaysDate;


    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String UserId, name, role, LoginId, id;


    ProgressDialog progressDialog;


    public ViewAttendance() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_attendance, container, false);



        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();

        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();


        className = view.findViewById(R.id.classList);
        recyclerView = view.findViewById(R.id.recyclerview);
        currentDate = view.findViewById(R.id.currentDate);
        back = view.findViewById(R.id.back);
        forword = view.findViewById(R.id.forword);
        sunday = view.findViewById(R.id.sunday);
        llmain = view.findViewById(R.id.llmain);


        classModulesList = new ArrayList<>();
        subModuleModelArrayList = new ArrayList<>();
        modelArrayList = new ArrayList<>();
        tempData = new ArrayList<>();
        holidayModelArrayList = new ArrayList<>();

        getStudentData();

        getAttendanceData();

        getHolidayData();


        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        //  System.out.println(dateFormat.format(date));

        currentDate.setText(dateFormat.format(date));
        todaysDate=currentDate.getText().toString();


        className.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    tempData = new ArrayList<>();

                    String selectedItem = parent.getSelectedItem().toString();

                    StanderdId = classModulesList.get(position).getStandardId();
                    DivisionId = classModulesList.get(position).getDivisionId();

                    if (!selectedItem.equalsIgnoreCase("Select Class")) {

                        for (int i = 0; i < modelArrayList.size(); i++) {

                            if (StanderdId.equalsIgnoreCase(modelArrayList.get(i).getStandId()) && DivisionId.equalsIgnoreCase(modelArrayList.get(i).getDivId()) && todaysDate.equalsIgnoreCase(modelArrayList.get(i).getDate()) ) {

                                String Id = modelArrayList.get(i).getId();
                                String StudentName =  modelArrayList.get(i).getStudentName();
                                String RollNo =  modelArrayList.get(i).getRollNo();
                                String Date =  modelArrayList.get(i).getDate();
                                String PresentAbsent =  modelArrayList.get(i).getPresentAbsent();
                                String StandId =  modelArrayList.get(i).getStandId();
                                String DivId =  modelArrayList.get(i).getDivId();

                                AttendanceModuleModel subModuleModel = new AttendanceModuleModel(Id, StudentName, RollNo, Date, PresentAbsent,StandId,DivId);
                                tempData.add(subModuleModel);
                            }
                        }

                        if (tempData.isEmpty()) {
                            recyclerView.setAdapter(null);
                        } else {
                            LinearLayoutManager manager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(manager);
                            AttendanceAdapter adapter = new AttendanceAdapter(getContext(), tempData);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sunday.setVisibility(View.INVISIBLE);
                sunday.setText("");

                c.add(Calendar.DAY_OF_MONTH, -1);
                String newDate = sdf.format(c.getTime());

                currentDate.setText(newDate);

                todaysDate=currentDate.getText().toString();

                for (int i=0;i<holidayModelArrayList.size();i++){

                    String aaa=holidayModelArrayList.get(i).getStartDate();
                    if (aaa.compareTo(todaysDate)==0){
                        sunday.setVisibility(View.VISIBLE);
                        sunday.setText("Its Holiday");
                        recyclerView.setAdapter(null);
                    }
                }

                try {
                    Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(todaysDate);

                    Calendar c = Calendar.getInstance();
                    c.setTime(date1);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                    if (dayOfWeek==1){
                        sunday.setVisibility(View.VISIBLE);
                        sunday.setText("Its Sunday");
                        recyclerView.setAdapter(null);
                    }else {

                        tempData = new ArrayList<>();

                        for (int i = 0; i < modelArrayList.size(); i++) {

                            if (StanderdId.equalsIgnoreCase(modelArrayList.get(i).getStandId()) && DivisionId.equalsIgnoreCase(modelArrayList.get(i).getDivId()) && todaysDate.equalsIgnoreCase(modelArrayList.get(i).getDate()) ) {

                                String Id = modelArrayList.get(i).getId();
                                String StudentName =  modelArrayList.get(i).getStudentName();
                                String RollNo =  modelArrayList.get(i).getRollNo();
                                String Date =  modelArrayList.get(i).getDate();
                                String PresentAbsent =  modelArrayList.get(i).getPresentAbsent();
                                String StandId =  modelArrayList.get(i).getStandId();
                                String DivId =  modelArrayList.get(i).getDivId();

                                AttendanceModuleModel subModuleModel = new AttendanceModuleModel(Id, StudentName, RollNo, Date, PresentAbsent,StandId,DivId);
                                tempData.add(subModuleModel);
                            }
                        }

                        if (tempData.isEmpty()) {
                            recyclerView.setAdapter(null);
                        } else {
                            LinearLayoutManager manager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(manager);
                            AttendanceAdapter adapter = new AttendanceAdapter(getContext(), tempData);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                        }

                    }


                  //  Log.d("dayOfWeek", String.valueOf(dayOfWeek));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        forword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sunday.setVisibility(View.INVISIBLE);
                sunday.setText("");

                c.add(Calendar.DAY_OF_MONTH, 1);
                String newDate = sdf.format(c.getTime());

                currentDate.setText(newDate);

                todaysDate=currentDate.getText().toString();


                for (int i=0;i<holidayModelArrayList.size();i++){
                    String aaa=holidayModelArrayList.get(i).getStartDate();
                    if (aaa.compareTo(todaysDate)==0){
                        sunday.setVisibility(View.VISIBLE);
                        sunday.setText("Its Holiday");
                        recyclerView.setAdapter(null);
                    }
                }

                try {

                    Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(todaysDate);

                    Calendar c = Calendar.getInstance();
                    c.setTime(date1);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

                    if (dayOfWeek==1){
                        sunday.setVisibility(View.VISIBLE);
                        sunday.setText("Its Sunday");
                        recyclerView.setAdapter(null);
                    }else {

                        tempData = new ArrayList<>();

                        for (int i = 0; i < modelArrayList.size(); i++) {

                            if (StanderdId.equalsIgnoreCase(modelArrayList.get(i).getStandId()) && DivisionId.equalsIgnoreCase(modelArrayList.get(i).getDivId()) && todaysDate.equalsIgnoreCase(modelArrayList.get(i).getDate())) {

                                String Id = modelArrayList.get(i).getId();
                                String StudentName = modelArrayList.get(i).getStudentName();
                                String RollNo = modelArrayList.get(i).getRollNo();
                                String Date = modelArrayList.get(i).getDate();
                                String PresentAbsent = modelArrayList.get(i).getPresentAbsent();
                                String StandId = modelArrayList.get(i).getStandId();
                                String DivId = modelArrayList.get(i).getDivId();

                                AttendanceModuleModel subModuleModel = new AttendanceModuleModel(Id, StudentName, RollNo, Date, PresentAbsent, StandId, DivId);
                                tempData.add(subModuleModel);
                            }
                        }

                        if (tempData.isEmpty()) {
                            recyclerView.setAdapter(null);
                        } else {
                            LinearLayoutManager manager = new LinearLayoutManager(getContext());
                            recyclerView.setLayoutManager(manager);
                            AttendanceAdapter adapter = new AttendanceAdapter(getContext(), tempData);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                        }
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        return view;
    }

    private void getStudentData() {

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.GET, Url.GetAllClassesList, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                classnamelist = new ArrayList<>();
                //  classnamelist.add("Select Class");

                Log.d("res", response.toString());

                try {
                    progressDialog.cancel();
                    progressDialog.hide();

                    JSONArray jsonArray = response.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Id = jsonObject.getString("Id");
                        String StandardId = jsonObject.getString("StandardId");
                        String DivisionId = jsonObject.getString("DivisionId");
                        String standardNm = jsonObject.getString("standardNm");
                        String Division = jsonObject.getString("Division");

                        String DivisionName = standardNm + "-" + Division;

                        ClassModule classModule = new ClassModule(Id, StandardId, DivisionId, standardNm, Division);

                        classnamelist.add(DivisionName);

                        classModulesList.add(classModule);
                    }

                    ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, classnamelist);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    className.setAdapter(adapter);

                } catch (JSONException e) {

                    progressDialog.cancel();
                    progressDialog.hide();

                    Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                progressDialog.cancel();
                progressDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void getAttendanceData() {

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.GET, Url.Attendance, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Log.d("res", response.toString());

                try {
                    progressDialog.cancel();
                    progressDialog.hide();

                    JSONArray jsonArray = response.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Id = jsonObject.getString("Id");
                        String StudentName = jsonObject.getString("StudentName");
                        String RollNo = jsonObject.getString("RollNo");
                        String Date = jsonObject.getString("Date");
                        String PresentAbsent = jsonObject.getString("PresentAbsent");
                        String StandId = jsonObject.getString("StandId");
                        String DivId = jsonObject.getString("DivId");

                        AttendanceModuleModel moduleModel = new AttendanceModuleModel(Id, StudentName, RollNo, Date, PresentAbsent, StandId,DivId);
                        modelArrayList.add(moduleModel);

                    }

                    if (tempData.size() == 0) {
                        recyclerView.setAdapter(null);
                        //  Toast.makeText(getContext(), "No Data Available...", Toast.LENGTH_SHORT).show();
                    } else {

                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);
                        AttendanceAdapter adapter = new AttendanceAdapter(getContext(), null);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }


                } catch (JSONException e) {

                    progressDialog.cancel();
                    progressDialog.hide();

                    Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                progressDialog.cancel();
                progressDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);

    }

    private void getHolidayData() {

        Map<String,String> params=new HashMap<>();
        params.put("UserId",id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.Holiday, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Log.d("res", response.toString());

                try {

                    String success = response.getString("success");

                    JSONArray jsonArray = response.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Id = jsonObject.getString("Id");
                        String StartDate = jsonObject.getString("startDate");
                        String EndDate = jsonObject.getString("endDate");
                        String TotalDays = jsonObject.getString("totalDay");
                        String Name = jsonObject.getString("Name");

                        HolidayModel holidayModel=new HolidayModel(Id,StartDate,EndDate,TotalDays,Name,null,null,null,null,null,null,null);
                        holidayModelArrayList.add(holidayModel);

                        Collections.sort(holidayModelArrayList,HolidayModel.DateComparator);
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
