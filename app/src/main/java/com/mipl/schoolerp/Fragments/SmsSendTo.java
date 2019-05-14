package com.mipl.schoolerp.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.StudentListAdapter;
import com.mipl.schoolerp.Adapter.StudentListAdapterSms;
import com.mipl.schoolerp.Adapter.TeacherListAdapter;
import com.mipl.schoolerp.Adapter.TeacherListAdapterSms;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class SmsSendTo extends Fragment {

    String UserId, name, role, LoginId, id;

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    CheckBox SmsEntireSchool,SmsPrincipal,SmsSoft;
    RadioButton SmsTeacher,SmsStudent,SmsAdmin;
    static EditText SmsRecipient;

    RecyclerView recyclerView;

    CardView cardSpinner,cardRecycler;
    CheckBox StaticStudId;
    TextView StaticStudentName;
    Spinner classnameslist;
    Button btnBack;

    ArrayList<TeacherModel> teacherList;
    ArrayList<String> names;
    ArrayList<String> classnamelist;
    ArrayList<ClassModule> classNameList;
    ArrayList<StudentDetailsModule> StudentDetailsList;

    String PrincipalName,PrincipalId,PrincipalRole;
    String StanderdId,DivisionId;



    FragmentManager fragmentManager;



    public SmsSendTo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sms_send_to, container, false);

        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();


        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");


        names=new ArrayList<>();
        classNameList=new ArrayList<>();
        StudentDetailsList=new ArrayList<>();




        FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i = 2; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }



        SmsEntireSchool=view.findViewById(R.id.SmsEntireSchool);
        SmsPrincipal=view.findViewById(R.id.SmsPrincipal);
        SmsSoft=view.findViewById(R.id.SmsSoft);
        SmsTeacher=view.findViewById(R.id.SmsTeacher);
        SmsStudent=view.findViewById(R.id.SmsStudent);
        SmsAdmin=view.findViewById(R.id.SmsAdmin);
        SmsRecipient=view.findViewById(R.id.SmsRecipient);
        btnBack=view.findViewById(R.id.btnBack);

        recyclerView=view.findViewById(R.id.recyclerview);
        classnameslist=view.findViewById(R.id.classnames);

        cardSpinner=view.findViewById(R.id.cardSpinner);
        cardRecycler=view.findViewById(R.id.cardRecycler);

        StaticStudId=view.findViewById(R.id.StaticStudId);
        StaticStudentName=view.findViewById(R.id.StaticStudentName);




        if (name.equalsIgnoreCase("admin")){
            SmsEntireSchool.setVisibility(View.VISIBLE);
            SmsPrincipal.setVisibility(View.VISIBLE);
            getPrincipal();
        }else {
            SmsEntireSchool.setVisibility(View.GONE);
        }

        if (role.equalsIgnoreCase("2")){
            SmsPrincipal.setVisibility(View.VISIBLE);
            getPrincipal();   // Get Principal Deatils Here.
        }

        if (role.equalsIgnoreCase("3")){
            SmsPrincipal.setVisibility(View.INVISIBLE);
        }


        try{
            StringBuilder builder = new StringBuilder();
            for (SendDetailsModule details : Url.ArrayListOfDetailsSms) {
                builder.append(details.getName() + ";");
            }
            String aaa=builder.toString();
            SmsRecipient.setText(aaa);
            SmsRecipient.setTextSize(12);
        }catch (Exception e){
            e.printStackTrace();
        }




        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new SmsCenter();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });







        SmsPrincipal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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




        SmsSoft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked==true){
                    names.add(SmsSoft.getText().toString());
                    for (int i=0;i<names.size();i++){
                        SmsRecipient.setText(names.get(i)+";");
                    }
                }else if (isChecked==false){
                    names.remove(SmsSoft.getText().toString());

                    if (names.size()>0){
                        for (int i=0;i<names.size();i++){
                            SmsRecipient.setText(names.get(i)+";");
                        }
                    }else {
                        SmsRecipient.setText("");
                    }

                }
            }
        });


        SmsTeacher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (InternetConnection.isInternetAvailable(getContext())){

                    if (isChecked){


                        cardRecycler.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        classnameslist.setVisibility(View.GONE);

                        recyclerView.setNestedScrollingEnabled(false);

                        StaticStudId.setVisibility(View.GONE);
                        StaticStudentName.setVisibility(View.GONE);

                        getTeacherData();

                    }else {

                    }
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });






        SmsStudent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (InternetConnection.isInternetAvailable(getContext())){

                    if (isChecked){

                        recyclerView.setVisibility(View.GONE);
                        cardRecycler.setVisibility(View.GONE);
                        cardSpinner.setVisibility(View.VISIBLE);
                        classnameslist.setVisibility(View.VISIBLE);

                        getStudentData();

                    }else {

                    }

                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }


            }
        });

        classnameslist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction()==MotionEvent.ACTION_UP){

                    try{

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

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });





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

                            Url.ArrayListOfDetailsSms.add(sendDetailsModule);

                            SetDataEdittext(Url.ArrayListOfDetailsSms);
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

                            for (int j=0;j<Url.ArrayListOfDetailsSms.size();j++){
                                SendDetailsModule DetailsModule=Url.ArrayListOfDetailsSms.get(j);
                                if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                    Url.ArrayListOfDetailsSms.remove(j);
                                }
                            }

                            SetDataEdittext(Url.ArrayListOfDetailsSms);
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


                        JSONArray jsonArray = response.getJSONArray("list");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            final JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String TeacherId = jsonObject.getString("TeacherId");
                            String TeacherName = jsonObject.getString("Name");
                            String TeacherRole = jsonObject.getString("role");

                            TeacherModel teacherModel=new TeacherModel();

                            boolean isChecked = false;

                            if (Url.ArrayListOfDetailsSms.size()>0){
                                for (int j=0;j<Url.ArrayListOfDetailsSms.size();j++){
                                    SendDetailsModule sendDetailsModule=Url.ArrayListOfDetailsSms.get(j);

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
                        TeacherListAdapterSms adapter = new TeacherListAdapterSms(getContext(),teacherList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }

                } catch (JSONException e) {

                    Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

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


                    Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

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

                        if (Url.ArrayListOfDetailsSms.size()>0){
                            for (int j=0;j<Url.ArrayListOfDetailsSms.size();j++){
                                SendDetailsModule sendDetailsModule=Url.ArrayListOfDetailsSms.get(j);

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


                        StudentListAdapterSms adapter = new StudentListAdapterSms(getContext(),StudentDetailsList);
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


                    Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

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
            SmsRecipient.setText(aaa);
            SmsRecipient.setTextSize(12);

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


                    PrincipalId=response.getString("TeacherId");
                    PrincipalName=response.getString("Name");
                    PrincipalRole=response.getString("Role");

                } catch (JSONException e) {


                    Toast.makeText(getContext(), "Network Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

}
