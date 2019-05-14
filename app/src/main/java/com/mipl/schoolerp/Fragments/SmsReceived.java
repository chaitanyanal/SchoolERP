package com.mipl.schoolerp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mipl.schoolerp.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class SmsReceived extends Fragment {


    public SmsReceived() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sms_inbox, container, false);


        return view;
    }

}
