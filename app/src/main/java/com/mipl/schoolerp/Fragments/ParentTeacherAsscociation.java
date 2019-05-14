package com.mipl.schoolerp.Fragments;


import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.AnnualPlannerAdapter;
import com.mipl.schoolerp.Adapter.HolidayAdapter;
import com.mipl.schoolerp.Adapter.ParentTeacherAssociationAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.HolidayModel;
import com.mipl.schoolerp.Model.ParentTeacherModel;
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

public class ParentTeacherAsscociation extends Fragment {


    ImageView EnlargeFirst,EnlargeSecond;
    RecyclerView Teacher,Parent;
    TextView txt1,txt2;

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String UserId, name, role, LoginId, id;

    ProgressDialog dialog;


    ArrayList<ParentTeacherModel> ParentNameList;
    ArrayList<ParentTeacherModel> TeacherNameList;




    public ParentTeacherAsscociation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_parent_teacher_asscociation, container, false);

        EnlargeFirst=view.findViewById(R.id.EnlargeFirst);
        EnlargeSecond=view.findViewById(R.id.EnlargeSecond);
        Teacher=view.findViewById(R.id.Teacher);
        Parent=view.findViewById(R.id.Parent);
        txt1=view.findViewById(R.id.txt1);
        txt2=view.findViewById(R.id.txt2);

        Teacher.setVisibility(View.GONE);
        Parent.setVisibility(View.GONE);


        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();

        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();


        ParentNameList=new ArrayList<>();
        TeacherNameList=new ArrayList<>();


        getParentData();

        getTeacherData();


        EnlargeFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Teacher.getVisibility()==View.VISIBLE){
                    Teacher.setVisibility(View.GONE);
                }else {

                    Teacher.setVisibility(View.VISIBLE);
                    Parent.setVisibility(View.GONE);
                }
            }
        });

        EnlargeSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Parent.getVisibility()==View.VISIBLE){
                    Parent.setVisibility(View.GONE);
                }else {

                    Teacher.setVisibility(View.GONE);
                    Parent.setVisibility(View.VISIBLE);
                }
            }
        });


        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Teacher.getVisibility()==View.VISIBLE){
                    Teacher.setVisibility(View.GONE);
                }else {
                    Teacher.setVisibility(View.VISIBLE);
                    Parent.setVisibility(View.GONE);
                }


            }
        });

        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Parent.getVisibility()==View.VISIBLE){
                    Parent.setVisibility(View.GONE);
                }else {

                    Teacher.setVisibility(View.GONE);
                    Parent.setVisibility(View.VISIBLE);
                }
            }
        });



        return view;
    }



    private void getParentData() {

        Map<String,String> params=new HashMap<>();
        params.put("UserId",id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.ParentData, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                 Log.d("res", response.toString());

                try {
                    dialog.dismiss();
                    dialog.hide();


                    JSONArray jsonArray = response.getJSONArray("ParentList");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Id = jsonObject.getString("Id");
                        String Name = jsonObject.getString("Name");

                        ParentTeacherModel parentTeacherModel=new ParentTeacherModel(Id,Name);
                        ParentNameList.add(parentTeacherModel);

                    }

                    if (ParentNameList.size() > 0) {
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        Parent.setLayoutManager(manager);
                        ParentTeacherAssociationAdapter adapter = new ParentTeacherAssociationAdapter(getContext(), ParentNameList);
                        Parent.setHasFixedSize(true);
                        Parent.setAdapter(adapter);
                    }


                    /*if (holidayModelArrayList.size()==0){
                        dialog.dismiss();
                        dialog.hide();

                        Toast.makeText(getContext(), "No Data Available...", Toast.LENGTH_SHORT).show();
                    }*/

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


    private void getTeacherData() {

        Map<String,String> params=new HashMap<>();
        params.put("UserId",id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.TeacherData, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                 Log.d("res", response.toString());

                try {
                    dialog.dismiss();
                    dialog.hide();

                    JSONArray jsonArray = response.getJSONArray("TeacherList");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Id = jsonObject.getString("Id");
                        String Name = jsonObject.getString("Name");

                        ParentTeacherModel parentTeacherModel=new ParentTeacherModel(Id,Name);
                        TeacherNameList.add(parentTeacherModel);
                    }

                    if (TeacherNameList.size() > 0) {
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        Teacher.setLayoutManager(manager);
                        ParentTeacherAssociationAdapter adapter = new ParentTeacherAssociationAdapter(getContext(), TeacherNameList);
                        Teacher.setHasFixedSize(true);
                        Teacher.setAdapter(adapter);
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
