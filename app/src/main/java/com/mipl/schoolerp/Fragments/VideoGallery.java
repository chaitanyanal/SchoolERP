package com.mipl.schoolerp.Fragments;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.HolidayAdapter;
import com.mipl.schoolerp.Adapter.PhotoGallaryAdapter;
import com.mipl.schoolerp.Adapter.VideoListAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.PhotoGalleryModel;
import com.mipl.schoolerp.Model.VideoGalleryModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 */

public class VideoGallery extends Fragment {

    Spinner monthList,yearList;
    String SelectedMonth,SelectedYear;
    LinearLayout VideoLayout;
    ProgressDialog dialog;

    RecyclerView recyclerVideoList;


    ArrayList<String> AcadamicYear;
    ArrayList<String> YearList;
    ArrayList<String> MonthList;


    ArrayList<VideoGalleryModel> galleryModelArrayList;
    ArrayList<VideoGalleryModel> tempList;

    public VideoGallery() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_video_gallery, container, false);


        monthList=view.findViewById(R.id.monthList);
        yearList=view.findViewById(R.id.yearList);
        VideoLayout=view.findViewById(R.id.VideoLayout);
        recyclerVideoList=view.findViewById(R.id.recyclerVideoList);
      //  VideoLayout.setVisibility(View.INVISIBLE);

        galleryModelArrayList=new ArrayList<>();
        MonthList=new ArrayList<>();
        AcadamicYear=new ArrayList<>();
        YearList=new ArrayList<>();
        tempList=new ArrayList<>();


        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.show();

        getAllVideos();


        monthList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tempList.clear();

                SelectedMonth=parent.getSelectedItem().toString();

                for (int i=0;i<galleryModelArrayList.size();i++){
                    if (SelectedMonth.equalsIgnoreCase(galleryModelArrayList.get(i).getMonth())&& SelectedYear.equalsIgnoreCase(galleryModelArrayList.get(i).getAcademicYear())){

                        String id1=galleryModelArrayList.get(i).getId();
                        String VideoDetails=galleryModelArrayList.get(i).getVideoDetails();
                        String VideoLink=galleryModelArrayList.get(i).getVideoLink();
                        String AcademicYear=galleryModelArrayList.get(i).getAcademicYear();
                        String Month=galleryModelArrayList.get(i).getMonth();

                        VideoGalleryModel videoGalleryModel=new VideoGalleryModel(id1,VideoDetails,VideoLink,AcademicYear,Month);

                        tempList.add(videoGalleryModel);
                    }
                }


                if (!tempList.isEmpty()) {
                    LinearLayoutManager manager = new LinearLayoutManager(getContext());
                    recyclerVideoList.setLayoutManager(manager);
                    VideoListAdapter adapter1 = new VideoListAdapter(getContext(), tempList);
                    recyclerVideoList.setHasFixedSize(true);
                    recyclerVideoList.setAdapter(adapter1);
                } else {
                    recyclerVideoList.setAdapter(null);
                  //  Toast.makeText(getContext(), "No Record Found...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        yearList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SelectedYear=parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }





    private void getAllVideos() {

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.GET, Url.GetAllVideos, null, new Response.Listener<JSONObject>() {
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
                        String VideoDetails = jsonObject.getString("VideoDetails");
                        String VideoLink = jsonObject.getString("VideoLink");
                        String AcademicYear= jsonObject.getString("AcademicYear");
                        String Month= jsonObject.getString("Month");

                        String mm=getMonth(Integer.parseInt(Month));

                        VideoGalleryModel videoGalleryModel=new VideoGalleryModel(Id,VideoDetails,VideoLink,AcademicYear,mm);
                        galleryModelArrayList.add(videoGalleryModel);

                        AcadamicYear.add(AcademicYear);
                    }


                    Set<String> unique = new HashSet<String>(AcadamicYear);
                    for (int j = 0; j < unique.size(); j++) {
                        YearList.add(unique.toString().replace("[","").replace("]",""));
                    }

                    ArrayAdapter<String> adapter =new ArrayAdapter<String>(getContext(),R.layout.sample_textview, YearList);
                    adapter.setDropDownViewResource( R.layout.sample_textview);
                    yearList.setAdapter(adapter);


                    for (int i=0;i<galleryModelArrayList.size();i++){
                        if (SelectedMonth.equalsIgnoreCase(galleryModelArrayList.get(i).getMonth())&& SelectedYear.equalsIgnoreCase(galleryModelArrayList.get(i).getAcademicYear())){

                            String id=galleryModelArrayList.get(i).getId();
                            String VideoDetails=galleryModelArrayList.get(i).getVideoDetails();
                            String VideoLink=galleryModelArrayList.get(i).getVideoLink();
                            String AcademicYear=galleryModelArrayList.get(i).getAcademicYear();
                            String Month=galleryModelArrayList.get(i).getMonth();

                            VideoGalleryModel videoGalleryModel=new VideoGalleryModel(id,VideoDetails,VideoLink,AcademicYear,Month);

                            tempList.add(videoGalleryModel);
                        }
                    }


                    if (tempList.size() > 0) {
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerVideoList.setLayoutManager(manager);
                        VideoListAdapter adapter1 = new VideoListAdapter(getContext(), tempList);
                        recyclerVideoList.setHasFixedSize(true);
                        recyclerVideoList.setAdapter(adapter1);
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

}
