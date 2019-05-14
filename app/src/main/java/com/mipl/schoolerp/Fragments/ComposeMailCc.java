package com.mipl.schoolerp.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.RadioButton;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.StudentListAdapter;
import com.mipl.schoolerp.Adapter.StudentListAdapterOne;
import com.mipl.schoolerp.Adapter.TeacherListAdapter;
import com.mipl.schoolerp.Adapter.TeacherListAdapterOne;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Helper.InternetConnection;
import com.mipl.schoolerp.Model.ClassModule;
import com.mipl.schoolerp.Model.SendDetailsModule;
import com.mipl.schoolerp.Model.StudentDetailsModule;
import com.mipl.schoolerp.Model.TeacherModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComposeMailCc extends Fragment {



    CheckBox EntireSchoolCc,PrincipalCc,softCc;
    RadioButton TeacherCc,StudentCc,AdminCc;
    static EditText RecipientCc;
    RecyclerView recyclerViewCc;
    Spinner classnameslistCc;
    Button btnBackCc;

    CheckBox StaticStudIdCc;
    TextView StaticStudentNameCc;

    CardView cardSpinnerCc,cardRecyclerCc;

    ArrayList<TeacherModel> teacherListCc;

    ArrayList<String> namesCc;
    ArrayList<String> classnamelistCc;

    ArrayList<ClassModule> classNameListCc;
    ArrayList<StudentDetailsModule> StudentDetailsListCc;

    String PrincipalNameCc,PrincipalIdCc,PrincipalRoleCc;


    ProgressDialog progressDialogCc;

    String StanderdIdCc,DivisionIdCc;

    String UserId, name, role, LoginId, id;

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public ComposeMailCc() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_compose_mail_cc, container, false);

        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();


        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");


        EntireSchoolCc=view.findViewById(R.id.EntireSchoolCc);
        PrincipalCc=view.findViewById(R.id.PrincipalCc);
        softCc=view.findViewById(R.id.softCc);
        TeacherCc=view.findViewById(R.id.TeacherCc);
        StudentCc=view.findViewById(R.id.StudentCc);
        AdminCc=view.findViewById(R.id.AdminCc);
        RecipientCc=view.findViewById(R.id.RecipientCc);
        recyclerViewCc=view.findViewById(R.id.recyclerviewCc);
        classnameslistCc=view.findViewById(R.id.classnamesCc);
        btnBackCc=view.findViewById(R.id.btnBackCc);

        cardSpinnerCc=view.findViewById(R.id.cardSpinnerCc);
        cardRecyclerCc=view.findViewById(R.id.cardRecyclerCc);

        StaticStudIdCc=view.findViewById(R.id.StaticStudIdCc);
        StaticStudentNameCc=view.findViewById(R.id.StaticStudentNameCc);

        PrincipalCc.setVisibility(View.INVISIBLE);
        EntireSchoolCc.setVisibility(View.INVISIBLE);

        RecipientCc.setClickable(true);
        RecipientCc.setEnabled(true);
        RecipientCc.setFocusable(false);

        RecipientCc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.RecipientCc) {
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

        RecipientCc.setScroller(new Scroller(getContext()));
        RecipientCc.setVerticalScrollBarEnabled(true);
        RecipientCc.setMovementMethod(new ScrollingMovementMethod());

        progressDialogCc=new ProgressDialog(getContext());

        namesCc=new ArrayList<>();
        classNameListCc=new ArrayList<>();
        StudentDetailsListCc=new ArrayList<>();



        if (name.equalsIgnoreCase("admin")){
            EntireSchoolCc.setVisibility(View.VISIBLE);
            PrincipalCc.setVisibility(View.VISIBLE);
            getPrincipalCc();
        }else {
            EntireSchoolCc.setVisibility(View.GONE);
        }

        if (role.equalsIgnoreCase("2")){
            PrincipalCc.setVisibility(View.VISIBLE);
            getPrincipalCc();   // Get Principal Deatils Here.
        }

        if (role.equalsIgnoreCase("3")){
            PrincipalCc.setVisibility(View.INVISIBLE);
            StudentCc.setVisibility(View.GONE);
        }



        try{
            StringBuilder builder = new StringBuilder();
            for (SendDetailsModule details : Url.ArrayListOfDetailsCc) {
                builder.append(details.getName() + ";");
            }

            if (Url.ArrayListOfDetailsCc.size()==0){
                RecipientCc.setText("");
            }else {
                String aaa=builder.toString();
                RecipientCc.setText(aaa);
                RecipientCc.setTextSize(12);

            }
        }catch (Exception e){
            e.printStackTrace();
        }




        EntireSchoolCc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    RecipientCc.setText("");
                    RecipientCc.setText(EntireSchoolCc.getText());

                    PrincipalCc.setVisibility(View.INVISIBLE);
                    softCc.setVisibility(View.INVISIBLE);
                    TeacherCc.setVisibility(View.INVISIBLE);
                    StudentCc.setVisibility(View.INVISIBLE);
                    AdminCc.setVisibility(View.INVISIBLE);

                    recyclerViewCc.setVisibility(View.GONE);
                    classnameslistCc.setVisibility(View.GONE);

                    cardSpinnerCc.setVisibility(View.GONE);
                    cardRecyclerCc.setVisibility(View.GONE);

                    StaticStudIdCc.setVisibility(View.GONE);
                    StaticStudentNameCc.setVisibility(View.GONE);


                }else {
                    RecipientCc.setText("");

                    PrincipalCc.setVisibility(View.VISIBLE);
                    softCc.setVisibility(View.VISIBLE);
                    TeacherCc.setVisibility(View.VISIBLE);
                    StudentCc.setVisibility(View.VISIBLE);
                    AdminCc.setVisibility(View.VISIBLE);

                    TeacherCc.setChecked(false);
                    StudentCc.setChecked(false);
                    AdminCc.setChecked(false);

                    recyclerViewCc.setVisibility(View.GONE);
                    classnameslistCc.setVisibility(View.GONE);

                    StaticStudIdCc.setVisibility(View.GONE);
                    StaticStudentNameCc.setVisibility(View.GONE);

                }
            }
        });




        PrincipalCc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (InternetConnection.isInternetAvailable(getContext())){

                    if (isChecked){

                        if (PrincipalNameCc!=null){

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setChecked(true);
                            sendDetailsModule.setName(PrincipalNameCc);
                            sendDetailsModule.setId(PrincipalIdCc);
                            sendDetailsModule.setRole(PrincipalRoleCc);

                            Url.PrincipalToIdCC=PrincipalIdCc;
                            Url.PrincipalToRoleCC=PrincipalRoleCc;

                            Url.ArrayListOfDetailsCc.add(sendDetailsModule);

                            SetDataEdittextCc(Url.ArrayListOfDetailsCc);
                        }
                    }else {

                        SendDetailsModule sendDetailsModule=new SendDetailsModule();
                        sendDetailsModule.setChecked(true);
                        sendDetailsModule.setName(PrincipalNameCc);
                        sendDetailsModule.setId(PrincipalIdCc);
                        sendDetailsModule.setRole(PrincipalRoleCc);

                        Url.PrincipalToIdCC="";
                        Url.PrincipalToRoleCC="";

                        for (int i=0;i<Url.ArrayListOfDetailsCc.size();i++){
                            SendDetailsModule DetailsModule=Url.ArrayListOfDetailsCc.get(i);
                            if (DetailsModule.getId().equalsIgnoreCase(PrincipalIdCc)){
                                Url.ArrayListOfDetailsCc.remove(i);
                                SetDataEdittextCc(Url.ArrayListOfDetailsCc);
                            }
                        }
                    }
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });



        softCc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked==true){
                    namesCc.add(softCc.getText().toString());
                    for (int i=0;i<namesCc.size();i++){
                        RecipientCc.setText(namesCc.get(i)+";");
                    }
                }else {
                    for (int i=0;i<namesCc.size();i++){
                        if (softCc.getText().toString().equalsIgnoreCase(namesCc.get(i))){
                            namesCc.remove(softCc.getText().toString());
                        }

                        if (namesCc.size()>0){
                            RecipientCc.setText(namesCc.get(i)+";");
                        }else {
                            RecipientCc.setText("");
                        }
                    }
                }
            }
        });


        TeacherCc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (InternetConnection.isInternetAvailable(getContext())){

                    if (isChecked){

                        progressDialogCc.setTitle("Loading..");
                        progressDialogCc.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialogCc.show();

                        cardRecyclerCc.setVisibility(View.VISIBLE);
                        recyclerViewCc.setVisibility(View.VISIBLE);
                        classnameslistCc.setVisibility(View.GONE);

                        recyclerViewCc.setNestedScrollingEnabled(false);

                        StaticStudIdCc.setVisibility(View.GONE);
                        StaticStudentNameCc.setVisibility(View.GONE);

                        getTeacherDataCc();

                    }else {
                        progressDialogCc.cancel();
                        progressDialogCc.hide();
                    }
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });


        StudentCc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (InternetConnection.isInternetAvailable(getContext())){

                    if (isChecked){
                        progressDialogCc.setTitle("Loading..");
                        progressDialogCc.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialogCc.show();

                        recyclerViewCc.setVisibility(View.GONE);
                        cardRecyclerCc.setVisibility(View.GONE);
                        cardSpinnerCc.setVisibility(View.VISIBLE);
                        classnameslistCc.setVisibility(View.VISIBLE);

                        getStudentDataCc();

                    }else {
                        progressDialogCc.cancel();
                        progressDialogCc.hide();
                    }

                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });


        AdminCc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cardSpinnerCc.setVisibility(View.GONE);
                recyclerViewCc.setVisibility(View.GONE);

                StaticStudIdCc.setVisibility(View.GONE);
                StaticStudentNameCc.setVisibility(View.GONE);
            }
        });


        classnameslistCc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_UP){

                    final CharSequence[] classList = classnamelistCc.toArray(new String[classnamelistCc.size()]);
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

                    dialogBuilder.setTitle("Class List");
                    dialogBuilder.setItems(classList, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            String selectedText = classList[item].toString();
                            Log.d("aaa",selectedText);

                            StanderdIdCc=classNameListCc.get(item).getStandardId();
                            DivisionIdCc=classNameListCc.get(item).getDivisionId();

                            Log.d("bbb",classNameListCc.get(item).getStandardId());
                            Log.d("ccc",classNameListCc.get(item).getDivisionId());

                            Url.classFullName=selectedText;

                            StaticStudentNameCc.setText(selectedText);

                            getStandardViseStudentListCc();

                        }
                    });

                    AlertDialog alertDialogObject = dialogBuilder.create();
                    alertDialogObject.show();
                }
                return false;
            }
        });




        btnBackCc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment2 = new MessageCenter();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment2);
                fragmentTransaction.commit();

            }
        });



        StaticStudIdCc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                try{
                    if (isChecked){

                        for(int i=0;i<StudentDetailsListCc.size();i++)
                        {
                            StudentDetailsModule dtm = StudentDetailsListCc.get(i);
                            dtm.setChecked(true);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setName(StudentDetailsListCc.get(i).getStudentName());
                            sendDetailsModule.setId(StudentDetailsListCc.get(i).getStudId());
                            sendDetailsModule.setRole(StudentDetailsListCc.get(i).getRole());

                            Url.ArrayListOfDetailsCc.add(sendDetailsModule);

                            SetDataEdittextCc(Url.ArrayListOfDetailsCc);
                        }
                        StudentListAdapter adapter = new StudentListAdapter(getContext(),StudentDetailsListCc);
                        recyclerViewCc.setHasFixedSize(true);
                        recyclerViewCc.setAdapter(adapter);

                    }else {

                        for(int i=0;i< StudentDetailsListCc.size();i++)
                        {
                            StudentDetailsModule dtm = StudentDetailsListCc.get(i);
                            dtm.setChecked(false);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setName(StudentDetailsListCc.get(i).getStudentName());
                            sendDetailsModule.setId(StudentDetailsListCc.get(i).getStudId());
                            sendDetailsModule.setRole(StudentDetailsListCc.get(i).getRole());

                            String Tid=StudentDetailsListCc.get(i).getStudId();

                            for (int j=0;j<Url.ArrayListOfDetailsCc.size();j++){
                                SendDetailsModule DetailsModule=Url.ArrayListOfDetailsCc.get(j);
                                if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                    Url.ArrayListOfDetailsCc.remove(j);
                                }
                            }

                            SetDataEdittextCc(Url.ArrayListOfDetailsCc);
                        }

                        StudentListAdapter adapter = new StudentListAdapter(getContext(),StudentDetailsListCc);
                        recyclerViewCc.setHasFixedSize(true);
                        recyclerViewCc.setAdapter(adapter);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        StaticStudentNameCc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void getPrincipalCc(){

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.GET, Url.PrincipalName, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());

                try {
                    progressDialogCc.cancel();
                    progressDialogCc.hide();

                    PrincipalIdCc=response.getString("TeacherId");
                    PrincipalNameCc=response.getString("Name");
                    PrincipalRoleCc=response.getString("Role");

                } catch (JSONException e) {

                    progressDialogCc.cancel();
                    progressDialogCc.hide();

                    Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                progressDialogCc.cancel();
                progressDialogCc.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void getTeacherDataCc() {

        Map<String,String> params=new HashMap<>();
        params.put("UserID",id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.GetAllTeacher, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());
                teacherListCc=new ArrayList<>();
                teacherListCc.clear();

                try {
                    String success = response.getString("status");

                    if (success.equalsIgnoreCase("0")) {

                        progressDialogCc.cancel();
                        progressDialogCc.hide();

                        JSONArray jsonArray = response.getJSONArray("list");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            final JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String TeacherId = jsonObject.getString("TeacherId");
                            String TeacherName = jsonObject.getString("Name");
                            String TeacherRole = jsonObject.getString("role");

                            TeacherModel teacherModel=new TeacherModel();

                            boolean isChecked = false;

                            if (Url.ArrayListOfDetailsCc.size()>0){
                                for (int j=0;j<Url.ArrayListOfDetailsCc.size();j++){
                                    SendDetailsModule sendDetailsModule=Url.ArrayListOfDetailsCc.get(j);

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
                            teacherListCc.add(teacherModel);
                        }

                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerViewCc.setLayoutManager(manager);
                        TeacherListAdapterOne adapter = new TeacherListAdapterOne(getContext(),teacherListCc);
                        recyclerViewCc.setHasFixedSize(true);
                        recyclerViewCc.setAdapter(adapter);
                    }

                } catch (JSONException e) {

                    progressDialogCc.cancel();
                    progressDialogCc.hide();

                    Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                progressDialogCc.cancel();
                progressDialogCc.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);

    }

    private void getStudentDataCc(){

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.GET, Url.GetAllClassesList, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                classnamelistCc=new ArrayList<>();

                Log.d("res", response.toString());

                try {
                    progressDialogCc.cancel();
                    progressDialogCc.hide();

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

                        classnamelistCc.add(DivisionName);

                        classNameListCc.add(classModule);
                    }

                } catch (JSONException e) {

                    progressDialogCc.cancel();
                    progressDialogCc.hide();

                    Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                progressDialogCc.cancel();
                progressDialogCc.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

    private void getStandardViseStudentListCc(){

        Map<String, String> params = new HashMap<String, String>();

        params.put("StandardId", StanderdIdCc);
        params.put("DivisionId", DivisionIdCc);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.GetStandDivisionWiseStudent, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());

                StudentDetailsListCc=new ArrayList<>();

                try {
                    progressDialogCc.cancel();
                    progressDialogCc.hide();

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

                        if (Url.ArrayListOfDetailsCc.size()>0){
                            for (int j=0;j<Url.ArrayListOfDetailsCc.size();j++){
                                SendDetailsModule sendDetailsModule=Url.ArrayListOfDetailsCc.get(j);

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

                        StudentDetailsListCc.add(studModel);
                    }

                    if (StudentDetailsListCc.size()>0){

                        recyclerViewCc.setVisibility(View.VISIBLE);
                        cardRecyclerCc.setVisibility(View.VISIBLE);

                        StaticStudIdCc.setVisibility(View.VISIBLE);
                        StaticStudentNameCc.setVisibility(View.VISIBLE);

                        recyclerViewCc.setNestedScrollingEnabled(false);

                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerViewCc.setLayoutManager(manager);
                        recyclerViewCc.setHasFixedSize(true);


                        StudentListAdapterOne adapter = new StudentListAdapterOne(getContext(),StudentDetailsListCc);
                        recyclerViewCc.setHasFixedSize(true);
                        recyclerViewCc.setAdapter(adapter);

                    }else {

                        StaticStudIdCc.setVisibility(View.GONE);
                        StaticStudentNameCc.setVisibility(View.GONE);

                        recyclerViewCc.setVisibility(View.GONE);
                        cardRecyclerCc.setVisibility(View.GONE);

                        Toast.makeText(getContext(), "No Data Available...", Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {

                    progressDialogCc.cancel();
                    progressDialogCc.hide();

                    Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

                progressDialogCc.cancel();
                progressDialogCc.hide();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);

    }

    public void SetDataEdittextCc(ArrayList<SendDetailsModule>  data){

        try{
            StringBuilder builder = new StringBuilder();
            for (SendDetailsModule details : data) {
                builder.append(details.getName() + ";");
            }

            if (Url.ArrayListOfDetailsCc.size()==0){
                RecipientCc.setText("");
            }else {
                String aaa=builder.toString();
                RecipientCc.setText(aaa);
                RecipientCc.setTextSize(12);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
