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
import com.mipl.schoolerp.Adapter.SentMessageAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.SentViewModel;
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
public class SentFragment extends Fragment {

    RecyclerView recyclerViewSent;
    ArrayList<SentViewModel> sentViewModelArrayList;

    String UserId, name, role, LoginId, id;

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public SentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_sent, container, false);

        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();

        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");


        recyclerViewSent=view.findViewById(R.id.recyclerviewsent);
        sentViewModelArrayList=new ArrayList<>();

        getSentData();

        return view;
    }

    private void getSentData() {

        Map<String,String> params=new HashMap<>();
        params.put("UserId",id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.SentView, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                 Log.d("res", response.toString());

                try {
                    JSONArray jsonArray = response.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Id = jsonObject.getString("Id");
                        String toList = jsonObject.getString("Tolist");
                        String Subject = jsonObject.getString("Subject");
                        String msgbody = jsonObject.getString("msgbody");
                        String date = jsonObject.getString("date");
                        String Attachment1 = jsonObject.getString("Attachment1");
                        String File1 = jsonObject.getString("File1Title");
                        String Attachment2 = jsonObject.getString("Attachment2");
                        String File2 = jsonObject.getString("File2Title");
                        String Attachment3 = jsonObject.getString("Attachment3");
                        String File3 = jsonObject.getString("File3Title");

                        SentViewModel sentViewModel=new SentViewModel(Id,toList,Subject,msgbody,date,Attachment1,Attachment2,Attachment3,File1,File2,File3);
                        sentViewModelArrayList.add(sentViewModel);
                    }

                    if (sentViewModelArrayList.size()>0){
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerViewSent.setLayoutManager(manager);
                        SentMessageAdapter adapter = new SentMessageAdapter(getContext(),sentViewModelArrayList);
                        recyclerViewSent.setHasFixedSize(true);
                        recyclerViewSent.setAdapter(adapter);
                    }else {
                       // Toast.makeText(getContext(), "No Records Found.", Toast.LENGTH_SHORT).show();
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
