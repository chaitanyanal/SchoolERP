package com.mipl.schoolerp.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mipl.schoolerp.Adapter.StaffBirthdayAdapter;
import com.mipl.schoolerp.Adapter.SubjectTeacherAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.SendDetailsModule;
import com.mipl.schoolerp.Model.StudentDetailsModule;
import com.mipl.schoolerp.Model.TeacherListModel;
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

public class StudentSubjectTeacher extends Fragment {

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    RecyclerView recyclerView;
    TextView subName,teacherName,text,classTeacherName;
    LinearLayout linearLayout;

    String UserId, name, role, LoginId, id;

    ArrayList<TeacherListModel> teacherArrayList;

    ProgressDialog dialog;


    public StudentSubjectTeacher() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_student_subject_teacher, container, false);


        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();

        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");

        teacherArrayList=new ArrayList<>();


        recyclerView=view.findViewById(R.id.recyclerview);
        subName=view.findViewById(R.id.subName);
        teacherName=view.findViewById(R.id.teacherName);
        classTeacherName=view.findViewById(R.id.classTeacherName);
        text=view.findViewById(R.id.text);
        linearLayout=view.findViewById(R.id.ll);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();


        getAllTeacherList();

        getClassTeacher();

        return view;
    }

    private void getAllTeacherList() {

        Map<String,String> params=new HashMap<>();
        params.put("UserId",id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.AllSubjectTeacher, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());

                try {
                    dialog.dismiss();
                    dialog.hide();

                    JSONArray jsonArray = response.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String TeacherId=jsonObject.getString("TeacherId");
                        String Teachername=jsonObject.getString("Teachername");
                        String SubjectName=jsonObject.getString("SubjectName");

                        if (!Teachername.equalsIgnoreCase("null")){
                            TeacherListModel teacherListModel=new TeacherListModel(TeacherId,Teachername,SubjectName);
                            teacherArrayList.add(teacherListModel);
                        }
                    }

                    if (teacherArrayList.size() > 0) {
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);
                        SubjectTeacherAdapter adapter = new SubjectTeacherAdapter(getContext(), teacherArrayList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }else {

                        subName.setVisibility(View.INVISIBLE);
                        teacherName.setVisibility(View.INVISIBLE);
                        linearLayout.setVisibility(View.INVISIBLE);
                        recyclerView.setAdapter(null);

                        text.setVisibility(View.VISIBLE);
                        text.setText("No Records Found");
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

    private void getClassTeacher(){

        Map<String,String> params=new HashMap<>();
        params.put("UserId",id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.ClassTeacherName, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                dialog.dismiss();
                dialog.hide();

                Log.d("res", response.toString());

                try {
                    String Name=response.getString("data");
                    classTeacherName.setText(Name);

                } catch (JSONException e) {
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
