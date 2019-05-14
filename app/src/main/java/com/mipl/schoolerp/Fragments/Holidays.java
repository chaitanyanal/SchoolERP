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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.HolidayAdapter;
import com.mipl.schoolerp.Adapter.PhotoGallaryAdapter;
import com.mipl.schoolerp.Adapter.StaffBirthdayAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.HolidayModel;
import com.mipl.schoolerp.Model.StaffBirthdayModule;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Holidays extends Fragment {

    RecyclerView recyclerView;
    ArrayList<HolidayModel> holidayModelArrayList;

    ProgressDialog dialog;

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String UserId, name, role, LoginId, id;

    public Holidays() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_holidays, container, false);

        recyclerView=view.findViewById(R.id.holidayList);
        holidayModelArrayList=new ArrayList<>();


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

        getHolidayData();

        return view;
    }

    private void getHolidayData() {

        Map<String,String> params=new HashMap<>();
        params.put("UserId",id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.Holiday, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Log.d("res", response.toString());

                try {
                    dialog.dismiss();
                    dialog.hide();

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

                    if (holidayModelArrayList.size() > 0) {
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);
                        HolidayAdapter adapter = new HolidayAdapter(getContext(), holidayModelArrayList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }

                    if (holidayModelArrayList.size()==0){
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
