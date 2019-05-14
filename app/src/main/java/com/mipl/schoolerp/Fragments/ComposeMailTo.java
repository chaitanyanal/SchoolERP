package com.mipl.schoolerp.Fragments;



import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mipl.schoolerp.Adapter.TeacherListAdapter;
import com.mipl.schoolerp.Adapter.StudentListAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Helper.InternetConnection;
import com.mipl.schoolerp.Helper.VolleyMultipartRequest;
import com.mipl.schoolerp.Interfaces.TeacherDetailsInterface;
import com.mipl.schoolerp.Model.ClassModule;
import com.mipl.schoolerp.Model.SendDetailsModule;
import com.mipl.schoolerp.Model.StudentDetailsModule;
import com.mipl.schoolerp.Model.TeacherModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * A simple {@link Fragment} subclass.
 */


public class ComposeMailTo extends Fragment {

    CheckBox EntireSchool,Principal,soft;
    RadioButton Teacher,Student,Admin;
    static EditText Recipient;
    RecyclerView recyclerView;
    Spinner classnameslist;
    Button btnBack;

    CheckBox StaticStudId;
    TextView StaticStudentName;

    CardView cardSpinner,cardRecycler;

    ArrayList<TeacherModel> teacherList;

    ArrayList<String> names;
    ArrayList<String> classnamelist;

    ArrayList<ClassModule> classNameList;
    ArrayList<StudentDetailsModule> StudentDetailsList;

    String PrincipalName,PrincipalId,PrincipalRole;
    ProgressDialog progressDialog;
    String StanderdId,DivisionId;

    String UserId, name, role, LoginId, id;

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    public ComposeMailTo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_compose_mail, container, false);


        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();


        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");


        EntireSchool=view.findViewById(R.id.EntireSchool);
        Principal=view.findViewById(R.id.Principal);
        soft=view.findViewById(R.id.soft);
        Teacher=view.findViewById(R.id.Teacher);
        Student=view.findViewById(R.id.Student);
        Admin=view.findViewById(R.id.Admin);
        Recipient=view.findViewById(R.id.Recipient);
        recyclerView=view.findViewById(R.id.recyclerview);
        classnameslist=view.findViewById(R.id.classnames);
        btnBack=view.findViewById(R.id.btnBack);

        cardSpinner=view.findViewById(R.id.cardSpinner);
        cardRecycler=view.findViewById(R.id.cardRecycler);

        StaticStudId=view.findViewById(R.id.StaticStudId);
        StaticStudentName=view.findViewById(R.id.StaticStudentName);


        Principal.setVisibility(View.INVISIBLE);
        EntireSchool.setVisibility(View.INVISIBLE);

        Recipient.setClickable(true);
        Recipient.setEnabled(true);
        Recipient.setFocusable(false);

        Recipient.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.Recipient) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        Recipient.setScroller(new Scroller(getContext()));
        Recipient.setVerticalScrollBarEnabled(true);
        Recipient.setMovementMethod(new ScrollingMovementMethod());

        progressDialog=new ProgressDialog(getContext());

        names=new ArrayList<>();
        classNameList=new ArrayList<>();
        StudentDetailsList=new ArrayList<>();





        if (name.equalsIgnoreCase("admin")){
            EntireSchool.setVisibility(View.VISIBLE);
            Principal.setVisibility(View.VISIBLE);
            getPrincipal();
        }else {
            EntireSchool.setVisibility(View.GONE);
        }

        if (role.equalsIgnoreCase("2")){
            Principal.setVisibility(View.VISIBLE);
            getPrincipal();   // Get Principal Deatils Here.
        }

        if (role.equalsIgnoreCase("3")){
            Principal.setVisibility(View.INVISIBLE);
            Student.setVisibility(View.GONE);
        }





        try{
            StringBuilder builder = new StringBuilder();
            for (SendDetailsModule details : Url.ArrayListOfDetails) {
                builder.append(details.getName() + ";");
            }
            String aaa=builder.toString();
            Recipient.setText(aaa);
            Recipient.setTextSize(12);
        }catch (Exception e){
            e.printStackTrace();
        }



        EntireSchool.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    Recipient.setText("");
                    Recipient.setText(EntireSchool.getText());

                    Principal.setVisibility(View.INVISIBLE);
                    soft.setVisibility(View.INVISIBLE);
                    Teacher.setVisibility(View.INVISIBLE);
                    Student.setVisibility(View.INVISIBLE);
                    Admin.setVisibility(View.INVISIBLE);

                    recyclerView.setVisibility(View.GONE);
                    classnameslist.setVisibility(View.GONE);

                    cardSpinner.setVisibility(View.GONE);
                    cardRecycler.setVisibility(View.GONE);

                    StaticStudId.setVisibility(View.GONE);
                    StaticStudentName.setVisibility(View.GONE);

                }else {
                    Recipient.setText("");

                    Principal.setVisibility(View.VISIBLE);
                    soft.setVisibility(View.VISIBLE);
                    Teacher.setVisibility(View.VISIBLE);
                    Student.setVisibility(View.VISIBLE);
                    Admin.setVisibility(View.VISIBLE);

                    Teacher.setChecked(false);
                    Student.setChecked(false);
                    Admin.setChecked(false);

                    recyclerView.setVisibility(View.GONE);
                    classnameslist.setVisibility(View.GONE);

                    StaticStudId.setVisibility(View.GONE);
                    StaticStudentName.setVisibility(View.GONE);

                }
            }
        });

        Principal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (InternetConnection.isInternetAvailable(getContext())){

                    if (isChecked){

                        if (PrincipalName!=null){

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setChecked(true);
                            sendDetailsModule.setName(PrincipalName);
                            sendDetailsModule.setId(PrincipalId);
                            sendDetailsModule.setRole(PrincipalRole);

                            Url.PrincipalToId=PrincipalId;
                            Url.PrincipalToRole=PrincipalRole;

                            Url.ArrayListOfDetails.add(sendDetailsModule);

                            SetDataEdittext(Url.ArrayListOfDetails);
                        }
                    }else {

                        SendDetailsModule sendDetailsModule=new SendDetailsModule();
                        sendDetailsModule.setChecked(true);
                        sendDetailsModule.setName(PrincipalName);
                        sendDetailsModule.setId(PrincipalId);
                        sendDetailsModule.setRole(PrincipalRole);

                        for (int i=0;i<Url.ArrayListOfDetails.size();i++){
                            SendDetailsModule DetailsModule=Url.ArrayListOfDetails.get(i);
                            if (DetailsModule.getId().equalsIgnoreCase(PrincipalId)){
                                Url.ArrayListOfDetails.remove(i);
                                SetDataEdittext(Url.ArrayListOfDetails);
                            }
                        }
                    }
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        soft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked==true){
                    names.add(soft.getText().toString());
                    for (int i=0;i<names.size();i++){
                        Recipient.setText(names.get(i)+";");
                    }
                }else if (isChecked==false){
                    names.remove(soft.getText().toString());

                    if (names.size()>0){
                        for (int i=0;i<names.size();i++){
                            Recipient.setText(names.get(i)+";");
                        }
                    }else {
                        Recipient.setText("");
                    }

                }
            }
        });

        Teacher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (InternetConnection.isInternetAvailable(getContext())){

                    if (isChecked){

                        progressDialog.setTitle("Loading..");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();

                        cardRecycler.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        classnameslist.setVisibility(View.GONE);

                        recyclerView.setNestedScrollingEnabled(false);

                        StaticStudId.setVisibility(View.GONE);
                        StaticStudentName.setVisibility(View.GONE);


                        getTeacherData();

                    }else {
                        progressDialog.cancel();
                        progressDialog.hide();
                    }
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }


            }
        });

        Student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (InternetConnection.isInternetAvailable(getContext())){

                    if (isChecked){
                        progressDialog.setTitle("Loading..");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();

                        recyclerView.setVisibility(View.GONE);
                        cardRecycler.setVisibility(View.GONE);
                        cardSpinner.setVisibility(View.VISIBLE);
                        classnameslist.setVisibility(View.VISIBLE);

                        getStudentData();

                    }else {
                        progressDialog.cancel();
                        progressDialog.hide();
                    }

                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }


            }
        });

        Admin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    cardSpinner.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);

                    StaticStudId.setVisibility(View.GONE);
                    StaticStudentName.setVisibility(View.GONE);

                }else {
                    progressDialog.cancel();
                    progressDialog.hide();
                }
            }
        });


        classnameslist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_UP){


                    final CharSequence[] classList = classnamelist.toArray(new String[classnamelist.size()]);
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

                    dialogBuilder.setTitle("Class List");
                    dialogBuilder.setItems(classList, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            String selectedText = classList[item].toString();
                            Log.d("aaa",selectedText);

                            StanderdId=classNameList.get(item).getStandardId();
                            DivisionId=classNameList.get(item).getDivisionId();

                            Log.d("bbb",classNameList.get(item).getStandardId());
                            Log.d("ccc",classNameList.get(item).getDivisionId());

                            Url.classFullName=selectedText;

                            StaticStudentName.setText(selectedText);

                            getStandardViseStudentList();

                        }
                    });

                    AlertDialog alertDialogObject = dialogBuilder.create();
                    alertDialogObject.show();
                }
                return false;
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment2 = new MessageCenter();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment2);
                fragmentTransaction.commit();

            }
        });


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Here All CheckBox selected.


        StaticStudId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                try{
                    if (isChecked){

                        int size = StudentDetailsList.size();
                        for(int i=0;i<size;i++)
                        {
                            StudentDetailsModule dtm = StudentDetailsList.get(i);
                            dtm.setChecked(true);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setName(StudentDetailsList.get(i).getStudentName());
                            sendDetailsModule.setId(StudentDetailsList.get(i).getStudId());
                            sendDetailsModule.setRole(StudentDetailsList.get(i).getRole());

                            Url.ArrayListOfDetails.add(sendDetailsModule);

                            SetDataEdittext(Url.ArrayListOfDetails);
                        }
                        StudentListAdapter adapter = new StudentListAdapter(getContext(),StudentDetailsList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);

                    }else {

                        int size = StudentDetailsList.size();
                        for(int i=0;i<size;i++)
                        {
                            StudentDetailsModule dtm = StudentDetailsList.get(i);
                            dtm.setChecked(false);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setName(StudentDetailsList.get(i).getStudentName());
                            sendDetailsModule.setId(StudentDetailsList.get(i).getStudId());
                            sendDetailsModule.setRole(StudentDetailsList.get(i).getRole());

                            String Tid=StudentDetailsList.get(i).getStudId();

                            for (int j=0;j<Url.ArrayListOfDetails.size();j++){
                                SendDetailsModule DetailsModule=Url.ArrayListOfDetails.get(j);
                                if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                    Url.ArrayListOfDetails.remove(j);
                                }
                            }

                            SetDataEdittext(Url.ArrayListOfDetails);
                        }

                        StudentListAdapter adapter = new StudentListAdapter(getContext(),StudentDetailsList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        StaticStudentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        return view;
    }

    private void getTeacherData() {

        Map<String,String> params=new HashMap<>();
        params.put("UserID",id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.GetAllTeacher, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());
                teacherList=new ArrayList<>();
                teacherList.clear();

                try {
                    String success = response.getString("status");

                    if (success.equalsIgnoreCase("0")) {

                        progressDialog.cancel();
                        progressDialog.hide();

                        JSONArray jsonArray = response.getJSONArray("list");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            final JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String TeacherId = jsonObject.getString("TeacherId");
                            String TeacherName = jsonObject.getString("Name");
                            String TeacherRole = jsonObject.getString("role");

                            TeacherModel teacherModel=new TeacherModel();

                            boolean isChecked = false;

                            if (Url.ArrayListOfDetails.size()>0){
                                for (int j=0;j<Url.ArrayListOfDetails.size();j++){
                                    SendDetailsModule sendDetailsModule=Url.ArrayListOfDetails.get(j);

                                    teacherModel.setTeacherName(TeacherName);
                                    teacherModel.setTeacherRole(TeacherRole);
                                    teacherModel.setTeacherId(TeacherId);

                                    if (sendDetailsModule.getId().equalsIgnoreCase(TeacherId) && sendDetailsModule.getRole().equalsIgnoreCase(TeacherRole)){
                                        isChecked=true;
                                    }
                                }
                            }

                            teacherModel.setTeacherName(TeacherName);
                            teacherModel.setTeacherRole(TeacherRole);
                            teacherModel.setTeacherId(TeacherId);
                            teacherModel.setChecked(isChecked);
                            teacherList.add(teacherModel);
                        }

                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);
                        TeacherListAdapter adapter = new TeacherListAdapter(getContext(),teacherList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }

                } catch (JSONException e) {

                    progressDialog.cancel();
                    progressDialog.hide();

                    Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                progressDialog.cancel();
                progressDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);

    }

    private void getStudentData(){

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.GET, Url.GetAllClassesList, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                classnamelist=new ArrayList<>();

                Log.d("res", response.toString());

                try {
                        progressDialog.cancel();
                        progressDialog.hide();

                        JSONArray jsonArray = response.getJSONArray("list");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            final JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String Id = jsonObject.getString("Id");
                            String StandardId = jsonObject.getString("StandardId");
                            String DivisionId = jsonObject.getString("DivisionId");
                            String standardNm = jsonObject.getString("standardNm");
                            String Division = jsonObject.getString("Division");

                            String DivisionName=standardNm+"-"+Division;

                            ClassModule classModule=new ClassModule(Id,StandardId,DivisionId,standardNm,Division);

                            classnamelist.add(DivisionName);

                            classNameList.add(classModule);
                        }

                } catch (JSONException e) {

                    progressDialog.cancel();
                    progressDialog.hide();

                    Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                progressDialog.cancel();
                progressDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void getStandardViseStudentList(){

        Map<String, String> params = new HashMap<String, String>();

        params.put("StandardId", StanderdId);
        params.put("DivisionId", DivisionId);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.GetStandDivisionWiseStudent, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());

                StudentDetailsList=new ArrayList<>();

                try {
                    progressDialog.cancel();
                    progressDialog.hide();

                    JSONArray jsonArray = response.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String StudId=jsonObject.getString("StudId");
                        String StandardId=jsonObject.getString("StandardId");
                        String DivisionId=jsonObject.getString("DivisionId");
                        String StudentName=jsonObject.getString("StudnetName");
                        String StudentRole=jsonObject.getString("role");

                        StudentDetailsModule studModel=new StudentDetailsModule();

                        boolean isChecked = false;

                        if (Url.ArrayListOfDetails.size()>0){
                            for (int j=0;j<Url.ArrayListOfDetails.size();j++){
                                SendDetailsModule sendDetailsModule=Url.ArrayListOfDetails.get(j);

                                if (sendDetailsModule.getId().equalsIgnoreCase(StudId) && sendDetailsModule.getRole().equalsIgnoreCase(StudentRole)){

                                    isChecked=true;
                                }
                            }
                        }

                        studModel.setStudentName(StudentName);
                        studModel.setDivisionId(DivisionId);
                        studModel.setStandardId(StandardId);
                        studModel.setStudId(StudId);
                        studModel.setChecked(isChecked);
                        studModel.setRole(StudentRole);

                        StudentDetailsList.add(studModel);
                    }

                    if (StudentDetailsList.size()>0){

                        recyclerView.setVisibility(View.VISIBLE);
                        cardRecycler.setVisibility(View.VISIBLE);

                        StaticStudId.setVisibility(View.VISIBLE);
                        StaticStudentName.setVisibility(View.VISIBLE);

                        recyclerView.setNestedScrollingEnabled(false);

                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setHasFixedSize(true);


                        StudentListAdapter adapter = new StudentListAdapter(getContext(),StudentDetailsList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);

                    }else {

                        StaticStudId.setVisibility(View.GONE);
                        StaticStudentName.setVisibility(View.GONE);

                        recyclerView.setVisibility(View.GONE);
                        cardRecycler.setVisibility(View.GONE);

                        Toast.makeText(getContext(), "No Data Available...", Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {

                    progressDialog.cancel();
                    progressDialog.hide();

                    Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                progressDialog.cancel();
                progressDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);

    }

    public void SetDataEdittext(ArrayList<SendDetailsModule>  data){

        try{
            StringBuilder builder = new StringBuilder();
            for (SendDetailsModule details : data) {
                builder.append(details.getName() + ";");
            }

           // Log.d("qqqq",builder.toString());

            String aaa=builder.toString();
            Recipient.setText(aaa);
            Recipient.setTextSize(12);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void getPrincipal(){

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.GET, Url.PrincipalName, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());

                try {
                    progressDialog.cancel();
                    progressDialog.hide();

                    PrincipalId=response.getString("TeacherId");
                    PrincipalName=response.getString("Name");
                    PrincipalRole=response.getString("Role");

                } catch (JSONException e) {

                    progressDialog.cancel();
                    progressDialog.hide();

                    Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                progressDialog.cancel();
                progressDialog.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }


}
