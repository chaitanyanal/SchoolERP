package com.mipl.schoolerp.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.mipl.schoolerp.Helper.InternetConnection;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardStudent extends Fragment {

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    FragmentManager fragmentManager;

    ImageView attendance,progress,fees,subject_teacher,exam_schedule,holidays,message_center,sms_center,school_notice,annual_planner,parent_teacher,photo_gallery,video_gallery,chg_pass;

    public DashboardStudent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dashboard_student, container, false);


        attendance=view.findViewById(R.id.attendance);
        progress=view.findViewById(R.id.progress);
        fees=view.findViewById(R.id.fees);
        message_center=view.findViewById(R.id.message_center);
        subject_teacher=view.findViewById(R.id.subject_teacher);
        exam_schedule=view.findViewById(R.id.exam_schedule);
        holidays=view.findViewById(R.id.holidays);
        sms_center=view.findViewById(R.id.sms_center);
        school_notice=view.findViewById(R.id.school_notice);
        annual_planner=view.findViewById(R.id.annual_planner);
        parent_teacher=view.findViewById(R.id.parent_teacher);
        photo_gallery=view.findViewById(R.id.photo_gallery);
        video_gallery=view.findViewById(R.id.video_gallery);
        chg_pass=view.findViewById(R.id.chg_pass);

        Url.ArrayListOfDetails.clear();
        Url.ArrayListOfDetailsCc.clear();
        Url.ArrayListOfDetailsSms.clear();

        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();

        editor.remove("Sub");
        editor.remove("Cont");
        editor.commit();


        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


        message_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new MessageCenter();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });


        photo_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new PhotoGallery();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }

            }
        });


        school_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new SchoolNitices();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holidays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new Holidays();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        chg_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new ChangePassword();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }

            }
        });


        annual_planner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new AnnualPlanner();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        sms_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new StudentSmsInbox();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }

            }
        });


        video_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new VideoGallery();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new StudentAttendance();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        fees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new StudentFeesReport();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }

            }
        });

        subject_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new StudentSubjectTeacher();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }

            }
        });


        parent_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new ParentTeacherAsscociation();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }

            }
        });



        exam_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new ExamSchedule();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }

            }
        });


        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (InternetConnection.isInternetAvailable(getContext())){
                    Fragment fragment = new StudentProgressReport();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Toast.makeText(getContext(), "No Internet Connection...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


}
