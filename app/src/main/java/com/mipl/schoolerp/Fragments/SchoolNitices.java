package com.mipl.schoolerp.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.PhotoGallaryAdapter;
import com.mipl.schoolerp.Adapter.SchoolNoticesAdapter;
import com.mipl.schoolerp.Adapter.StaffBirthdayAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.SchoolNoticeModel;
import com.mipl.schoolerp.Model.StaffBirthdayModule;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SchoolNitices extends Fragment {

    RecyclerView recyclerView;
    ArrayList<SchoolNoticeModel> noticeModelArrayList;
    ProgressDialog dialog;

    public SchoolNitices() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_school_nitices, container, false);


        recyclerView=view.findViewById(R.id.noticeList);
        noticeModelArrayList=new ArrayList<>();

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();

        getData();

        return view;
    }

    private void getData() {

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.GET, Url.SchoolNotices, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Log.d("res", response.toString());

                try {

                    String success = response.getString("success");

                    dialog.dismiss();
                    dialog.hide();

                    JSONArray jsonArray = response.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Id = jsonObject.getString("Id");
                        String Description = jsonObject.getString("Description");
                        String NoticeImage = jsonObject.getString("NoticeImage");
                        String NoticeFile = jsonObject.getString("NoticeFile");
                        String startDate = jsonObject.getString("startDate");
                        String EndDate = jsonObject.getString("EndDate");

                        SchoolNoticeModel module = new SchoolNoticeModel(Id, Description, NoticeImage,NoticeFile,startDate,EndDate);
                        noticeModelArrayList.add(module);
                    }

                    if (noticeModelArrayList.size() > 0) {
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);
                        SchoolNoticesAdapter adapter = new SchoolNoticesAdapter(getContext(), noticeModelArrayList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
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
