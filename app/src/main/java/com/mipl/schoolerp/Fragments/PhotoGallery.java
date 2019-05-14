package com.mipl.schoolerp.Fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.InboxAdapter;
import com.mipl.schoolerp.Adapter.PhotoGallaryAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.InboxViewModel;
import com.mipl.schoolerp.Model.PhotoGalleryModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/*
 * Created by Chaitanya.
 *
 */

public class PhotoGallery extends Fragment {

    GridView simpleGrid;

    ArrayList<String> imageslist;
    ArrayList<String> AcadamicYear;
    ArrayList<String> YearList;
    ArrayList<String> MonthList;
    ArrayList<PhotoGalleryModel> galleryModelArrayList;
    ArrayList<String> UniqueMonthList;

    ProgressDialog dialog;

    Spinner yearList,monthList;
    String year,month;


    public PhotoGallery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hoto_gallery, container, false);

        simpleGrid = view.findViewById(R.id.simpleGridView);
        yearList = view.findViewById(R.id.yearList);
        monthList = view.findViewById(R.id.month_list);

        imageslist = new ArrayList<>();
        AcadamicYear = new ArrayList<>();
        YearList = new ArrayList<>();
        MonthList = new ArrayList<>();
        UniqueMonthList = new ArrayList<>();
        galleryModelArrayList = new ArrayList<>();

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();


        getAllPhotos();


        monthList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               imageslist.clear();

               month =parent.getSelectedItem().toString();

               for (int i=0;i<galleryModelArrayList.size();i++){
                   if (month.equalsIgnoreCase(galleryModelArrayList.get(i).getCreatedDate())&& year.equalsIgnoreCase(galleryModelArrayList.get(i).getAcademicYear())){
                       imageslist.add(galleryModelArrayList.get(i).getImagePath());
                   }
               }

               if (imageslist.size()>0){
                   PhotoGallaryAdapter customAdapter = new PhotoGallaryAdapter(getContext(), imageslist);
                   simpleGrid.setAdapter(customAdapter);
               }else {
                   simpleGrid.setAdapter(null);
                  // Toast.makeText(getContext(), "No Record Found...", Toast.LENGTH_SHORT).show();
               }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yearList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               // Log.d("321",parent.getSelectedItem().toString());
                year =parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Bundle bundle = new Bundle();
                bundle.putString("image", imageslist.get(position));

                Fragment fragment = new FullIImageView();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void getAllPhotos() {

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.GET, Url.GetAllPhotos, null, new Response.Listener<JSONObject>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(JSONObject response) {

                 Log.d("res", response.toString());

                try {

                    dialog.dismiss();
                    dialog.hide();

                    String success = response.getString("success");

                    JSONArray jsonArray = response.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Id = jsonObject.getString("Id");
                        String GalleryName = jsonObject.getString("GalleryName");
                        String ImagePath = jsonObject.getString("ImagePath");
                        String AcademicYear= jsonObject.getString("AcademicYear");
                        String CreatedDate= jsonObject.getString("CreatedDate");

                       // imageslist.add(ImagePath);
                        AcadamicYear.add(AcademicYear);

                        String mm=getMonth(Integer.parseInt(CreatedDate));
                        MonthList.add(mm);

                        PhotoGalleryModel photoGalleryModel=new PhotoGalleryModel(Id,GalleryName,ImagePath,AcademicYear,mm);
                        galleryModelArrayList.add(photoGalleryModel);
                    }

                    Set<String> unique = new HashSet<String>(AcadamicYear);
                    for (int j = 0; j < unique.size(); j++) {
                       // Log.d("zazaza", uniqueGas.toString());
                        YearList.add(unique.toString().replace("[","").replace("]",""));
                    }

                    ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(), R.layout.sample_textview, YearList);
                    adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                    yearList.setAdapter(adapter);

                    /*PhotoGallaryAdapter customAdapter = new PhotoGallaryAdapter(getContext(), imageslist);
                    simpleGrid.setAdapter(customAdapter);*/

                    try{

                        for (int i=0;i<galleryModelArrayList.size();i++){
                            if (month.equalsIgnoreCase(galleryModelArrayList.get(i).getCreatedDate())&& year.equalsIgnoreCase(galleryModelArrayList.get(i).getAcademicYear())){
                                imageslist.add(galleryModelArrayList.get(i).getImagePath());
                            }
                        }

                        if (imageslist.size()>0){
                            PhotoGallaryAdapter customAdapter = new PhotoGallaryAdapter(getContext(), imageslist);
                            simpleGrid.setAdapter(customAdapter);
                        }

                    }catch (Exception e){

                    }


                    Set<String> uniqueMonth = new HashSet<String>(MonthList);
                    for (int j = 0; j < uniqueMonth.size(); j++) {
                        UniqueMonthList.add(uniqueMonth.toString().replace("[","").replace("]",""));
                    }


                    for (int i = 0; i < UniqueMonthList.size(); i++) {
                        Log.d("mmm",UniqueMonthList.get(i));
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

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
