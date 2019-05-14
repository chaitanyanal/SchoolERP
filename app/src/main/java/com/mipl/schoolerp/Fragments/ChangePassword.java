package com.mipl.schoolerp.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.InboxAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.InboxViewModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePassword extends Fragment {

    TextInputEditText  oldPass,newPass,confirmPass;
    Button submit,cancel;

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String UserId, name, role, LoginId, id;

    ProgressDialog dialog;


    public ChangePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_change_password, container, false);

        oldPass=view.findViewById(R.id.oldPass);
        newPass=view.findViewById(R.id.newPass);
        confirmPass=view.findViewById(R.id.confirmPass);
        submit=view.findViewById(R.id.submit);
        cancel=view.findViewById(R.id.cancel);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Submiting...");



        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();

        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");

        init();

        return view;
    }

    private void init() {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Old=oldPass.getText().toString();
                String New=newPass.getText().toString();
                String Confirm=confirmPass.getText().toString();

                if (Old.isEmpty()){
                    oldPass.setError("Enter Old Password");
                    oldPass.requestFocus();
                    oldPass.setFocusable(true);
                }else if (New.isEmpty()){
                    newPass.setError("Enter New Password");
                    newPass.requestFocus();
                    newPass.setFocusable(true);
                }else if (Confirm.isEmpty()){
                    confirmPass.setError("Enter Confirm Password");
                    confirmPass.requestFocus();
                    confirmPass.setFocusable(true);
                }else {

                    if (newPass.getText().toString().equals(confirmPass.getText().toString())){

                        Log.d("Old",Old);
                        Log.d("New",New);
                        Log.d("Confirm",Confirm);

                        dialog.show();

                        changePass();
                    }else {
                        dialog.dismiss();
                        dialog.hide();
                        Toast.makeText(getContext(), "Password Didn't matched...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new DashBoard();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }

    private void changePass() {

        Map<String,String> params=new HashMap<>();
        params.put("UserId",id);
        params.put("oldPassword",oldPass.getText().toString());
        params.put("password",confirmPass.getText().toString());

        Log.d("id",id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.ChangePass, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                 Log.d("res", response.toString());

                try {
                    String status=response.getString("status");
                    String message=response.getString("message");

                    if (status.equalsIgnoreCase("1")){

                        dialog.dismiss();
                        dialog.hide();

                        Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();

                        Fragment fragment = new DashBoard();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }else if (status.equalsIgnoreCase("2")){
                        dialog.dismiss();
                        dialog.hide();
                        Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
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
