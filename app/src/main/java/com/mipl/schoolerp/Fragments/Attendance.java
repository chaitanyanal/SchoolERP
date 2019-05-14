package com.mipl.schoolerp.Fragments;



import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Helper.InternetConnection;
import com.mipl.schoolerp.Model.ClassModule;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

public class Attendance extends Fragment {


    ArrayList<String> classnamelist;
    ArrayList<ClassModule> classModulesList;

    Spinner className;
    Button btnView,btnmissingAttendance;
    TextView currentDate,text;
    Button back, forword,save;
    CheckBox present,absent;
    EditText absent_numbers;

    String todaysDate,classname;
    int allPresent=0;
    int allAbsent=0;
    int count=0;
    int StanderdId,DivisionId;
    String Start,End;

    LinearLayout main;


    ArrayList<String> CommaSeprated;
    ArrayList<String> CommaSeprated1;


    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Calendar c = Calendar.getInstance();

    ProgressDialog progressDialog;


    public Attendance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_attendance, container, false);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading..");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();



        className = view.findViewById(R.id.classList);
        btnView = view.findViewById(R.id.view);
        btnmissingAttendance = view.findViewById(R.id.missing_attendance);
        currentDate = view.findViewById(R.id.currentDate);
        back = view.findViewById(R.id.back);
        forword = view.findViewById(R.id.forword);
        present = view.findViewById(R.id.present);
        absent = view.findViewById(R.id.absent);
        save = view.findViewById(R.id.save);
        absent_numbers = view.findViewById(R.id.absent_numbers);

        main = view.findViewById(R.id.main);
        text = view.findViewById(R.id.text);
        text.setVisibility(View.GONE);



        classModulesList=new ArrayList<>();
        CommaSeprated=new ArrayList<>();
        CommaSeprated1=new ArrayList<>();

        ClassModule classModule = new ClassModule("0", "0", "0", "0", "0");
        classModulesList.add(0,classModule);



        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        //  System.out.println(dateFormat.format(date));

        currentDate.setText(dateFormat.format(date));


        getStudentData();

        className.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                classname=parent.getSelectedItem().toString();

                StanderdId= Integer.parseInt(classModulesList.get(position).getStandardId());
                DivisionId= Integer.parseInt(classModulesList.get(position).getDivisionId());


              //  Log.d("aaa",classname);
              //  Log.d("bbb", String.valueOf(StanderdId));
              //  Log.d("ccc", String.valueOf(DivisionId));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {

                c.add(Calendar.DAY_OF_MONTH, -1);
                String newDate = sdf.format(c.getTime());

                currentDate.setText(newDate);

                todaysDate=currentDate.getText().toString();


                if (todaysDate.compareTo(dateFormat.format(date)) > 0) {

                    main.setVisibility(View.GONE);
                    text.setVisibility(View.VISIBLE);
                    text.setTextColor(Color.RED);

                }else {

                    main.setVisibility(View.VISIBLE);
                    text.setVisibility(View.GONE);
                }
            }
        });

        forword.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {

                c.add(Calendar.DAY_OF_MONTH, 1);
                String newDate = sdf.format(c.getTime());

                currentDate.setText(newDate);

                todaysDate=currentDate.getText().toString();

                if (todaysDate.compareTo(dateFormat.format(date)) > 0) {

                    main.setVisibility(View.GONE);
                    text.setVisibility(View.VISIBLE);
                    text.setTextColor(Color.RED);

                }else {

                    main.setVisibility(View.VISIBLE);
                    text.setVisibility(View.GONE);
                }

            }
        });


        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new ViewAttendance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });




        present.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    absent.setChecked(false);
                    allPresent=1;
                }else {
                    allAbsent=0;
                }
            }
        });


        absent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    present.setChecked(false);
                    allAbsent=1;
                }else {
                    allPresent=0;
                }
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    CommaSeprated.clear();
                    CommaSeprated1.clear();

                    String input=absent_numbers.getText().toString();

                    if (!input.isEmpty()){

                        String[] arrOfStr = input.split(",", 100);

                        for (String a : arrOfStr){
                            CommaSeprated.add(a);
                        }

                        for (int i=0;i<CommaSeprated.size();i++){
                            if (CommaSeprated.get(i).contains("-")){

                                String[] arrOfStr1 = CommaSeprated.get(i).split("-", 100);
                                for (String a : arrOfStr1){
                                    CommaSeprated1.add(a);
                                }
                            }
                        }


                        for (int i=0;i<CommaSeprated.size();i++) {

                            boolean retval = CommaSeprated.get(i).contains("-");

                            if (retval == true) {
                                CommaSeprated.remove(i);
                                i=0;
                            }
                        }
                    }


                    if (classname.equalsIgnoreCase("Select Class")){
                        Toast.makeText(getContext(), "Please select class...", Toast.LENGTH_SHORT).show();
                    }else if (allAbsent==0 && allPresent==0 && input.isEmpty() ){
                        Toast.makeText(getContext(), "Please select All present/Absent...", Toast.LENGTH_SHORT).show();
                    }else {

                        Log.d("classname",classname);
                        Log.d("present", String.valueOf(allPresent));
                        Log.d("date", currentDate.getText().toString());
                        Log.d("StanderdId", String.valueOf(StanderdId));
                        Log.d("DivisionId", String.valueOf(DivisionId));


                        Map<String,String> params=new HashMap<>();
                        params.put("Stand", String.valueOf(StanderdId));
                        params.put("Div", String.valueOf(DivisionId));
                        params.put("Present",String.valueOf(allPresent));
                        params.put("Date1",currentDate.getText().toString());
                        params.put("Absent",String.valueOf(allAbsent));


                        if (CommaSeprated.size()>0){

                            for (int i=0;i<CommaSeprated.size();i++){
                              //  Log.d("aaa",CommaSeprated.get(i));

                                params.put("list[" + (i) + "].StudentId",CommaSeprated.get(i));
                                params.put("list[" + (i) + "].Id","0");
                            }
                        }


                        if (CommaSeprated1.size()>0){

                            for (int i=0;i<CommaSeprated1.size();i++){

                                if (count==0){
                                    params.put("RangeList[" + (i) + "].FromNo",CommaSeprated1.get(i));
                                  //  Log.d("bbb",CommaSeprated1.get(i));
                                    Start=CommaSeprated1.get(i);
                                    count=1;
                                }else if (count==1){
                                    params.put("RangeList[" + (i) + "].ToNo",CommaSeprated1.get(i));
                                  //  Log.d("ccc",CommaSeprated1.get(i));
                                    End=CommaSeprated1.get(i);
                                    count=0;
                                }
                            }
                        }



                        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.AddAttendance, params, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                Log.d("res", response.toString());

                                try {

                                    String status=response.getString("status");

                                    Toast.makeText(getContext(), ""+status, Toast.LENGTH_SHORT).show();

                                    Fragment fragment = new DashBoard();
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();

                                error.printStackTrace();

                                progressDialog.cancel();
                                progressDialog.hide();
                            }
                        });

                        AppController.getInstance().addToRequestQueue(jsonObjRequest);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        btnmissingAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new MissingAttendance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
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
                classnamelist.add("Select Class");

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

                Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();

                error.printStackTrace();

                progressDialog.cancel();
                progressDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }




}
