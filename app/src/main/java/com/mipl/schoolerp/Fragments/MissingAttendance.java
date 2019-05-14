package com.mipl.schoolerp.Fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import com.mipl.schoolerp.Adapter.MissingAttendanceAdapter;
import com.mipl.schoolerp.Adapter.SubjectTeacherAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.MissingAttendanceModel;
import com.mipl.schoolerp.Model.TeacherListModel;
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
public class MissingAttendance extends Fragment {

    RecyclerView recyclerView;
    Button back,forword;
    TextView currentDate,text;

    String todaysDate;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Calendar c = Calendar.getInstance();


    ArrayList<MissingAttendanceModel> attendanceModelArrayList;


    public MissingAttendance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_missing_attendance, container, false);

        recyclerView=view.findViewById(R.id.recyclerview);
        back=view.findViewById(R.id.back);
        forword=view.findViewById(R.id.forword);
        currentDate=view.findViewById(R.id.currentDate);
        text=view.findViewById(R.id.text);


        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        //  System.out.println(dateFormat.format(date));

        currentDate.setText(dateFormat.format(date));
        todaysDate=currentDate.getText().toString();

        getAttendance();


        back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {


                c.add(Calendar.DAY_OF_MONTH, -1);
                String newDate = sdf.format(c.getTime());

                currentDate.setText(newDate);

                todaysDate=currentDate.getText().toString();

                getAttendance();

                if (todaysDate.compareTo(dateFormat.format(date)) > 0) {

                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    text.setText("Future date attendance is not allowed...");
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

                getAttendance();

                if (todaysDate.compareTo(dateFormat.format(date)) > 0) {

                    text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    text.setText("Future date attendance is not allowed...");
                }

            }
        });

        return view;
    }


    private void getAttendance() {

        Map<String,String> params=new HashMap<>();
        params.put("date",todaysDate);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.MissingAttendance, params, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());

                try {
                    attendanceModelArrayList=new ArrayList<>();

                    JSONArray jsonArray = response.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Id = jsonObject.getString("Id");
                        String Name = jsonObject.getString("Name");

                        if (!Name.equalsIgnoreCase("null")){
                            MissingAttendanceModel missingAttendanceModel=new MissingAttendanceModel(Id,Name);
                            attendanceModelArrayList.add(missingAttendanceModel);
                        }
                    }

                    if (attendanceModelArrayList.size() > 0) {
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);
                        MissingAttendanceAdapter adapter = new MissingAttendanceAdapter(getContext(), attendanceModelArrayList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                        text.setText("Class Name List");
                    }else {
                        MissingAttendanceAdapter adapter = new MissingAttendanceAdapter(getContext(), attendanceModelArrayList);
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

}
