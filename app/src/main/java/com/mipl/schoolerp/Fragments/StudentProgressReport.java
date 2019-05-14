package com.mipl.schoolerp.Fragments;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.ExamScheduleModel;
import com.mipl.schoolerp.Model.MarksModel;
import com.mipl.schoolerp.Model.ProgressReportModelMain;
import com.mipl.schoolerp.Model.ProgressReportSubModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 */


public class StudentProgressReport extends Fragment {

    ScrollView scroll;
    HorizontalScrollView horizontalScrollView;

    TableLayout table;

    ArrayList<String> colomnList;
    ArrayList<String> rowList;
    ArrayList<String> marks;
    ArrayList<String> RowIdList;
    // ArrayList<String> newList;

    TableLayout tableLayout;
    LinearLayout.LayoutParams rprms;

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String UserId, name, role, LoginId, id;


    ArrayList<ExamScheduleModel> scheduleModelArrayList;

    ArrayList<ProgressReportModelMain> mainDataList;
    ArrayList<ProgressReportSubModel> subDataList;

    ArrayList<MarksModel> marksArrayList;


    Set<String> newList;


    String Value = "";


    ProgressDialog dialog;

    int counter=0;


    public StudentProgressReport() {
        // Required empty public constructor
    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_progress_report, container, false);

        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();

        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");

        tableLayout = view.findViewById(R.id.tableLayout);
        colomnList = new ArrayList<>();
        rowList = new ArrayList<>();


        RowIdList = new ArrayList<>();
        scheduleModelArrayList = new ArrayList<>();
        marksArrayList = new ArrayList<>();


        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();


        //    getSubjectData();
        //    getExamNameList();

        getProgressData();


        scroll = new ScrollView(getContext());
        scroll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        scroll.setPadding(10, 10, 10, 10);

        horizontalScrollView = new HorizontalScrollView(getContext());
        horizontalScrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        table = new TableLayout(getContext());
        colomnList.add(0, "");


        return view;
    }

    private void getProgressData() {

        Map<String, String> params = new HashMap<>();
        params.put("UserId", id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.ProgressReport, params, new Response.Listener<JSONObject>() {
            @TargetApi(Build.VERSION_CODES.N)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {

                //  modelArrayList=new ArrayList<>();
                marks = new ArrayList<>();

                mainDataList = new ArrayList<>();
                subDataList = new ArrayList<>();

                Log.d("res", response.toString());

                try {

                    dialog.dismiss();
                    dialog.hide();

                    JSONArray jsonArray = response.getJSONArray("MarkdetailList");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String ExamName = jsonObject.getString("ExamName");
                        String ExamId = jsonObject.getString("ExamId");

                        JSONArray jsonArray1 = jsonObject.getJSONArray("subjectlist");

                        for (int j = 0; j < jsonArray1.length(); j++) {

                            final JSONObject jsonObject1 = jsonArray1.getJSONObject(j);

                            String SubjectId = jsonObject1.getString("SubjectId");
                            String SubjectName = jsonObject1.getString("SubjectName");
                            String Mark = jsonObject1.getString("Mark");
                            String ExamId1 = jsonObject1.getString("ExamId");
                            String OutMark = jsonObject1.getString("OutMark");

                            ProgressReportSubModel subModel = new ProgressReportSubModel(SubjectId, SubjectName, Mark, ExamId1, OutMark);
                            subDataList.add(subModel);

                            String aaa = "" + Mark + "/" + "" + OutMark;
                            marks.add(aaa);

                            MarksModel marksModel = new MarksModel(ExamId1, aaa, SubjectName);
                            marksArrayList.add(marksModel);
                        }

                        ProgressReportModelMain progressReportModel = new ProgressReportModelMain(ExamName, ExamId, subDataList);
                        mainDataList.add(progressReportModel);

                        if (!ExamName.equalsIgnoreCase("null")){
                            rowList.add(ExamName);

                        }

                    }

                    for (int k = 0; k < subDataList.size(); k++) {
                        colomnList.add(subDataList.get(k).getSubjectName());
                    }
                    newList = new LinkedHashSet<String>(colomnList);


                    colomnList.clear();
                    if (newList.size()>1){
                        colomnList.addAll(newList);
                    }else {
                        Toast.makeText(getContext(), "No Data Available...", Toast.LENGTH_SHORT).show();
                    }


                    TableRow row1 = new TableRow(getContext());

                    for (int j = 0; j < colomnList.size(); j++) {

                        TextView tv12 = new TextView(getContext());
                        tv12.setText(colomnList.get(j));
                        tv12.setBackgroundResource(R.drawable.rect);
                        tv12.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        tv12.setWidth(300);
                        tv12.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                        tv12.setPadding(10, 10, 10, 10);
                        tv12.setTypeface(Typeface.DEFAULT_BOLD);
                        row1.addView(tv12);

                    }

                    table.addView(row1);

                    try {

                        for (int i = 0; i < rowList.size(); i++) {

                            final TableRow row = new TableRow(getContext());

                            TextView tv = new TextView(getContext());
                            tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                            tv.setText(rowList.get(i));
                            tv.setBackgroundResource(R.drawable.rect);
                            tv.setWidth(300);
                            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                            tv.setPadding(10, 10, 10, 10);
                            tv.setTypeface(Typeface.DEFAULT_BOLD);
                            row.addView(tv);

                            for (int j = 0; j < colomnList.size() - 1; j++) {

                                final TextView textView = new TextView(getContext());
                                textView.setText(marks.get(j));
                                textView.setBackgroundResource(R.drawable.rect);
                                textView.setPadding(15, 15, 15, 15);
                                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                textView.setSingleLine();

                                row.addView(textView);

                                Value = "";
                            }

                            int aa=colomnList.size()-1;

                            if (marks.size()>aa){

                                for (int k=0;k<aa;k++){
                                    marks.remove(0);
                                }
                            }

                            table.addView(row);
                        }

                        horizontalScrollView.addView(table);
                        scroll.addView(horizontalScrollView);

                        rprms = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                        rprms.setLayoutDirection(1);
                        rprms.resolveLayoutDirection(1);
                        tableLayout.addView(scroll);

                    } catch (Exception e) {
                        e.printStackTrace();
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

    private void getSubjectData() {

        Map<String, String> params = new HashMap<>();
        params.put("UserId", id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.SubjectData, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());

                try {

                    dialog.dismiss();
                    dialog.hide();


                    JSONArray jsonArray = response.getJSONArray("SubjectNamelist");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String SubjectId = jsonObject.getString("SubjectId");
                        String SubjectName = jsonObject.getString("SubjectName");

                        RowIdList.add(SubjectId);
                    }

                    /*if (plannerModelArrayList.size() > 0) {
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);
                        AnnualPlannerAdapter adapter = new AnnualPlannerAdapter(getContext(), plannerModelArrayList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(adapter);
                    }

                    if (plannerModelArrayList.size()==0){
                        dialog.dismiss();
                        dialog.hide();
                        Toast.makeText(getContext(), "No Data Available...", Toast.LENGTH_SHORT).show();
                    }*/

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

    private void getExamNameList() {

        Map<String, String> params = new HashMap<>();
        params.put("UserId", id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.ExamNameList, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());

                try {

                    dialog.dismiss();
                    dialog.hide();

                    JSONArray jsonArray = response.getJSONArray("ExamList");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String examId = jsonObject.getString("examId");
                        String ExamName = jsonObject.getString("ExamName");
                        String ExamDate = jsonObject.getString("ExamDate");
                        String Description = jsonObject.getString("Description");
                        String SubjectName= jsonObject.getString("SubjectName");


                        ExamScheduleModel examScheduleModel = new ExamScheduleModel(examId, ExamName,SubjectName, ExamDate, Description);
                        scheduleModelArrayList.add(examScheduleModel);

                        //  classNamelist.add(ExamName);
                    }

                    /*ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_dropdown_item, classNamelist);
                    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                    ExamName.setAdapter(adapter);*/


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
