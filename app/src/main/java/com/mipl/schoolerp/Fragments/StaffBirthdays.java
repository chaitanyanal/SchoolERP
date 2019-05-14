package com.mipl.schoolerp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.InboxAdapter;
import com.mipl.schoolerp.Adapter.StaffBirthdayAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Helper.InternetConnection;
import com.mipl.schoolerp.Model.StaffBirthdayModule;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class StaffBirthdays extends Fragment {

    TextView CurrentMonth;
    Button back, forword;

    RecyclerView recyclerView;

    ArrayList<StaffBirthdayModule> staffBirthdayModuleArrayList;

    ArrayList<StaffBirthdayModule> tempStaffBirthdayList;

    ArrayList<String> monthName;

    String getMonth;

    public StaffBirthdays() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_staff_birthdays, container, false);

        CurrentMonth = view.findViewById(R.id.month);
        back = view.findViewById(R.id.back);
        forword = view.findViewById(R.id.forword);
        recyclerView = view.findViewById(R.id.birthdayList);

        staffBirthdayModuleArrayList = new ArrayList<>();
        tempStaffBirthdayList = new ArrayList<>();
        monthName = new ArrayList<>();

        monthName.add("January");
        monthName.add("February");
        monthName.add("March");
        monthName.add("April");
        monthName.add("May");
        monthName.add("June");
        monthName.add("July");
        monthName.add("August");
        monthName.add("September");
        monthName.add("October");
        monthName.add("November");
        monthName.add("December");

        Format formatter = new SimpleDateFormat("MMMM");
        String s = formatter.format(new Date());
        System.out.println(s);

        CurrentMonth.setText(s);

        if (InternetConnection.isInternetAvailable(getContext())){

            getAllStaffBirthdayData();

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        String m = null;
                        String aaa = CurrentMonth.getText().toString();

                        if (aaa.equalsIgnoreCase("January")) {
                            m = monthName.get(monthName.size() - 1);
                            CurrentMonth.setText(m);
                        } else {

                            for (int i = 0; i < monthName.size(); i++) {
                                if (monthName.get(i).equalsIgnoreCase(CurrentMonth.getText().toString())) {
                                    m = monthName.get(i - 1);
                                }
                            }
                        }

                        CurrentMonth.setText(m);
                        tempStaffBirthdayList.clear();
                        getAllStaffBirthdayData();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            forword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        String m = null;
                        for (int i = 0; i < monthName.size(); i++) {
                            if (monthName.get(i).equalsIgnoreCase(CurrentMonth.getText().toString())) {
                                m = monthName.get(i + 1);
                            }

                            if (CurrentMonth.getText().toString().equalsIgnoreCase("December")) {
                                m = monthName.get(0);
                                CurrentMonth.setText(m);
                            }
                        }
                        CurrentMonth.setText(m);
                        tempStaffBirthdayList.clear();
                        getAllStaffBirthdayData();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return view;
    }

    private void getAllStaffBirthdayData() {

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.GET, Url.StaffBirthday, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String TeacherId = jsonObject.getString("TeacherId");
                        String Fullname = jsonObject.getString("Fullname");
                        String DOB = jsonObject.getString("DOB");

                        String[] separated = DOB.split("-");
                       // Log.d("1", separated[0]);
                       // Log.d("2", separated[1]);
                       // Log.d("3", separated[2]);

                        int m = Integer.parseInt(separated[1]);
                        getMonth(m);

                        StaffBirthdayModule module = new StaffBirthdayModule(TeacherId, Fullname, separated[2]);
                        staffBirthdayModuleArrayList.add(module);

                        if (getMonth.equalsIgnoreCase(CurrentMonth.getText().toString())) {
                            tempStaffBirthdayList.add(module);

                            Collections.sort(tempStaffBirthdayList, new Comparator<StaffBirthdayModule>() {
                                @Override
                                public int compare(StaffBirthdayModule o1, StaffBirthdayModule o2) {
                                    return o1.getDOB().compareTo(o2.getDOB());
                                }
                            });
                        }
                    }

                    if (staffBirthdayModuleArrayList.size() > 0) {
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);
                        StaffBirthdayAdapter adapter = new StaffBirthdayAdapter(getContext(), tempStaffBirthdayList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
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

    public void getMonth(int month) {
        Log.d("month", new DateFormatSymbols().getMonths()[month - 1]);
        getMonth = new DateFormatSymbols().getMonths()[month - 1];

    }

}
