package com.mipl.schoolerp.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.HolidayAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.HolidayModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentSmsInbox extends Fragment {


    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String UserId, name, role, LoginId, id;


    TextView mobileNo;
    LinearLayout linearLayout;


    public StudentSmsInbox() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_student_sms_inbox, container, false);


        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();

        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");

        mobileNo=view.findViewById(R.id.mobileNo);
        linearLayout=view.findViewById(R.id.SmsDetails);


        getParentMobileNo();


        return view;
    }

    private void getParentMobileNo() {

        Map<String,String> params=new HashMap<>();
        params.put("UserId",id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.ParentContatNo, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Log.d("res", response.toString());

                try {
                    JSONObject jsonObject=response.getJSONObject("data");

                    String mobile1 = jsonObject.getString("mobile1");
                    String mobile2 = jsonObject.getString("mobile2");

                    if (!mobile1.isEmpty() && !mobile2.isEmpty()){
                        mobileNo.setText(mobile1+","+mobile2);
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
