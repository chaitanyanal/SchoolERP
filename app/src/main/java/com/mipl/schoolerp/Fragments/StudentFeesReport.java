package com.mipl.schoolerp.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
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
import com.mipl.schoolerp.Adapter.AnnualPlannerAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.AnnualPlannerModel;
import com.mipl.schoolerp.Model.FeesModel;
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

public class StudentFeesReport extends Fragment {

    TextView totalApplicable, Fee_Paid, Fee_Payable, Late_Fee;
    LinearLayout linearLayout;

    TableLayout tableLayout;
    LinearLayout.LayoutParams rprms;

    ArrayList<FeesModel> modelArrayList;
    ArrayList<String> names;


    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String UserId, name, role, LoginId, id;


    TableLayout table;

    ScrollView scroll;
    HorizontalScrollView horizontalScrollView;

    Double TotalAmount = 0.0;
    Double TotalFeePaid = 0.0;
    Double TotalFeePayable = 0.0;
    Double TotalLateFee = 0.0;

    ProgressDialog dialog;


    public StudentFeesReport() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_fees_report, container, false);


        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();

        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");


        tableLayout = view.findViewById(R.id.table);
        totalApplicable = view.findViewById(R.id.totalApplicable);
        Fee_Paid = view.findViewById(R.id.Fee_Paid);
        Fee_Payable = view.findViewById(R.id.Fee_Payable);
        Late_Fee = view.findViewById(R.id.Late_Fee);
        linearLayout = view.findViewById(R.id.main);


        try {

            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Loading...");
            dialog.show();

            getFeesData();

            modelArrayList = new ArrayList<>();
            names = new ArrayList<>();

            names.add("FeeType");
            names.add("Payable For");
            names.add("Amount");
            names.add("DueDate");
            names.add("PaidDate");
            names.add("AmountPaid");
            names.add("AmountPayable");
            names.add("LateFees");


            scroll = new ScrollView(getContext());
            scroll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            scroll.setPadding(10, 10, 10, 10);

            horizontalScrollView = new HorizontalScrollView(getContext());
            horizontalScrollView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);


            table = new TableLayout(getContext());

            TableRow row1 = new TableRow(getContext());

            for (int j = 0; j < names.size(); j++) {

                TextView tv12 = new TextView(getContext());
                tv12.setText(names.get(j));
                tv12.setBackgroundResource(R.drawable.rect);
                tv12.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv12.setWidth(300);
                tv12.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                tv12.setPadding(20, 20, 20, 20);
                tv12.setTypeface(Typeface.DEFAULT_BOLD);
                row1.addView(tv12);
            }

            table.addView(row1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void getFeesData() {

        Map<String, String> params = new HashMap<>();
        params.put("UserId", id);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.Fees, params, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());

                try {

                    dialog.dismiss();
                    dialog.hide();

                    JSONArray jsonArray = response.getJSONArray("FeedetailList");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String FeeTypeName = jsonObject.getString("FeeTypeName");
                        String PayableFor = jsonObject.getString("PayableFor");
                        String Amount = jsonObject.getString("Amount");
                        String DueDate = jsonObject.getString("DueDate");
                        String PaidDate = jsonObject.getString("PaidDate");
                        String AmountPaid = jsonObject.getString("AmountPaid");
                        String AmountPayable = jsonObject.getString("AmountPayable");
                        String LateFees = jsonObject.getString("LateFee");

                        FeesModel feesModel = new FeesModel(FeeTypeName, PayableFor, Amount, DueDate, PaidDate, AmountPaid, AmountPayable, LateFees);
                        modelArrayList.add(feesModel);

                        double amount = Double.parseDouble(Amount);
                        TotalAmount = TotalAmount + amount;

                        double feepaid = Double.parseDouble(AmountPaid);
                        TotalFeePaid = TotalFeePaid + feepaid;

                        double feepayable = Double.parseDouble(AmountPayable);
                        TotalFeePayable = TotalFeePayable + feepayable;


                        double lateFee = Double.parseDouble(LateFees);
                        TotalLateFee = TotalLateFee + lateFee;
                    }

                    if (modelArrayList.size() > 0) {

                        for (int i = 0; i < modelArrayList.size(); i++) {

                            final TableRow row = new TableRow(getContext());

                            String FeeType = modelArrayList.get(i).getFeeTypeName();
                            String PayableFor = modelArrayList.get(i).getPayableFor();
                            String Amount = modelArrayList.get(i).getAmount();
                            String DueDate = modelArrayList.get(i).getDueDate();
                            String PaidDate = modelArrayList.get(i).getPaidDate();
                            String AmountPaid = modelArrayList.get(i).getAmountPaid();
                            String AmountPayable = modelArrayList.get(i).getAmountPayable();
                            String LateFees = modelArrayList.get(i).getLateFees();

                            String[] colText = {FeeType, PayableFor, Amount, DueDate, PaidDate, AmountPaid, AmountPayable, LateFees};

                            for (String text : colText) {

                                TextView tv1 = new TextView(getContext());
                                tv1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                tv1.setBackgroundResource(R.drawable.rect);
                                tv1.setText(text);
                                tv1.setSingleLine(true);
                                tv1.setWidth(300);
                                tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                tv1.setPadding(10, 10, 10, 10);
                                row.addView(tv1);
                            }

                            table.addView(row);
                            table.setPadding(10, 10, 10, 10);
                        }

                        horizontalScrollView.addView(table);
                        scroll.addView(horizontalScrollView);

                        rprms = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT);
                        rprms.setLayoutDirection(1);
                        rprms.resolveLayoutDirection(1);
                        tableLayout.addView(scroll);

                        totalApplicable.setText("" + TotalAmount);
                        Fee_Paid.setText("" + TotalFeePaid);
                        Fee_Payable.setText("" + TotalFeePayable);
                        Late_Fee.setText("" + TotalLateFee);
                    }else {
                        linearLayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "No Data Available...", Toast.LENGTH_SHORT).show();
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
