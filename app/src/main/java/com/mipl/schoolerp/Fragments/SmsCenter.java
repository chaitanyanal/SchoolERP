package com.mipl.schoolerp.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.SendDetailsModule;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmsCenter extends Fragment {

    EditText toMail,edtSubject,edtContent;
    Button btnAdd,btnSend,btnBack;


    public SmsCenter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sms_center, container, false);


        toMail=view.findViewById(R.id.toMail);
        edtSubject=view.findViewById(R.id.edtSubject);
        edtContent=view.findViewById(R.id.edtContent);
        btnAdd=view.findViewById(R.id.btnAdd);
        btnSend=view.findViewById(R.id.btnSend);
        btnBack=view.findViewById(R.id.btnBack);

        try{
            StringBuilder builder = new StringBuilder();
            for (SendDetailsModule details : Url.ArrayListOfDetailsSms) {
                builder.append(details.getName() + ";");
            }
            String aaa=builder.toString();
            toMail.setText(aaa);
            toMail.setTextSize(12);
        }catch (Exception e){
            e.printStackTrace();
        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SmsSendTo();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (toMail.getText().toString().isEmpty()){
                    toMail.setError("Please Add Receiptants...");
                }/*else if (edtSubject.getText().toString().isEmpty()){
                    edtSubject.setError("Please Add subject Text...");
                }*/else {
                    UPloadData();
                }
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
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

        return view;
    }

    private void UPloadData() {

        Map<String, String> params = new HashMap<>();
        params.put("messagebody",edtContent.getText().toString());
        params.put("Subject","test");

        for (int i = 0; i < Url.ArrayListOfDetailsSms.size(); i++) {

            SendDetailsModule detailsModule = Url.ArrayListOfDetailsSms.get(i);

            params.put("list[" + (i) + "].ToId", detailsModule.getId());
            params.put("list[" + (i) + "].ToRole", detailsModule.getRole());

        }

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.SMSCenter, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());

                try {
                    String status = response.getString("status");
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

                Toast.makeText(getContext(), "Server Error...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

}
