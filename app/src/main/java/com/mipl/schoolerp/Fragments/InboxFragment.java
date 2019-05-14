package com.mipl.schoolerp.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.InboxAdapter;
import com.mipl.schoolerp.Adapter.TeacherListAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.InboxViewModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InboxFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<InboxViewModel> ModelList;

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    ProgressDialog progressDialog;

    String UserId, name, role, LoginId, id;

    public InboxFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_inbox, container, false);

        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();

        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");

        recyclerView=view.findViewById(R.id.inbox_recyclerview);
        ModelList=new ArrayList<>();

        getInboxData();
        return view;
    }

    private void getInboxData() {

        Map<String,String>  params=new HashMap<>();
        params.put("Userid",id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.InboxView, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

               // Log.d("res", response.toString());

                try {
                    JSONArray jsonArray = response.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Id = jsonObject.getString("Id");
                        String fromId = jsonObject.getString("fromId");
                        String Subject = jsonObject.getString("Subject");
                        String msgbody = jsonObject.getString("msgbody");
                        String read = jsonObject.getString("read");
                        String date = jsonObject.getString("date");
                        String from = jsonObject.getString("fromName");
                        String Attachment1 = jsonObject.getString("Attachment1");
                        String File1 = jsonObject.getString("File1Title");
                        String Attachment2 = jsonObject.getString("Attachment2");
                        String File2 = jsonObject.getString("File2Title");
                        String Attachment3 = jsonObject.getString("Attachment3");
                        String File3 = jsonObject.getString("File3Title");

                        InboxViewModel viewModel=new InboxViewModel(Id,fromId,Subject,msgbody,read,date,from,Attachment1,Attachment2,Attachment3,File1,File2,File3);
                        ModelList.add(viewModel);
                    }

                    if (ModelList.size()>0){
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);
                        InboxAdapter adapter = new InboxAdapter(getContext(),ModelList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }else {
                      //  Toast.makeText(getContext(), "No Records Found.", Toast.LENGTH_SHORT).show();
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
