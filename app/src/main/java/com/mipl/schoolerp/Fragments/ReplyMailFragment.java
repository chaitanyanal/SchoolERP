package com.mipl.schoolerp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mipl.schoolerp.R;

/**
 * Created By Chaitanya.
 * Created :- 17/06/2019.
 */


public class ReplyMailFragment extends Fragment {

    TextView subject,ToName;
    EditText message;
    String File1,File2,File3,Attach1,Attach2,Attach3;



    public ReplyMailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reply_mail, container, false);


        Bundle b = this.getArguments();
        String from=b.getString("To");
        String Subject=b.getString("Subject");


        Log.d("123",from);
        Log.d("321",Subject);



        subject=view.findViewById(R.id.subject);
        ToName=view.findViewById(R.id.ToName);
        message=view.findViewById(R.id.message);


        subject.setText(Subject);
        ToName.setText(from);


        return view;
    }

}
