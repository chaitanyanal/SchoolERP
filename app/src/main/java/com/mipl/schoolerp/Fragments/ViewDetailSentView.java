package com.mipl.schoolerp.Fragments;


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Adapter.SentMessageAdapter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Helper.DownloadTask;
import com.mipl.schoolerp.Model.SentViewModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.mipl.schoolerp.Util.Url.Attachment1;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewDetailSentView extends Fragment {

    TextView subject,date,ToName,msgBody,txtfilename1,txtfilename2,txtfilename3;
    TextView view1,view2,view3;

    String Attach1,Attach2,Attach3,MsgId,File1,File2,File3;

    public ViewDetailSentView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_detail_sent_view, container, false);

        subject=view.findViewById(R.id.subjectSent);
        date=view.findViewById(R.id.dateSent);
        ToName=view.findViewById(R.id.ToNameSent);
        msgBody=view.findViewById(R.id.msgBodySent);

        view1=view.findViewById(R.id.view1);
        view2=view.findViewById(R.id.view2);
        view3=view.findViewById(R.id.view3);


        txtfilename1=view.findViewById(R.id.txtfilename1);
        txtfilename2=view.findViewById(R.id.txtfilename2);
        txtfilename3=view.findViewById(R.id.txtfilename3);

        Bundle b = this.getArguments();
        String from=b.getString("To");
        String Subject=b.getString("Subject");
        String Body=b.getString("Body");
        String Date=b.getString("Date");

        Attach1=b.getString("Attachment1");
        Attach2=b.getString("Attachment2");
        Attach3=b.getString("Attachment3");

        File1=b.getString("File1");
        File2=b.getString("File2");
        File3=b.getString("File3");


        MsgId=b.getString("msgId");




        subject.setText(Subject);
        date.setText(Date);
        ToName.setText(from);
        msgBody.setText(Html.fromHtml(Body));


        if (!File1.equalsIgnoreCase("null")){
            txtfilename1.setText(File1);
           // txtfilename1.setMovementMethod(LinkMovementMethod.getInstance());
            view1.setVisibility(View.VISIBLE);
        }else {
            view1.setVisibility(View.GONE);
        }

        if (!File2.equalsIgnoreCase("null")){
            txtfilename2.setText(Url.IP+Attach2);
            txtfilename2.setMovementMethod(LinkMovementMethod.getInstance());
            view2.setVisibility(View.VISIBLE);
        }else {
            view2.setVisibility(View.GONE);
        }

        if (!File3.equalsIgnoreCase("null")){
            txtfilename3.setText(Url.IP+Attach3);
            txtfilename3.setMovementMethod(LinkMovementMethod.getInstance());
            view3.setVisibility(View.VISIBLE);
        }else {
            view3.setVisibility(View.GONE);
        }




        txtfilename1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadFile(Url.IP + Attach1,File1);
            }
        });

        txtfilename2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadFile(Url.IP + Attach2,File2);
            }
        });

        txtfilename3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadFile(Url.IP + Attach3,File3);
            }
        });

        sendNotification();

        return view;
    }


    public void downloadFile(String uRl,String file) {
        File direct = new File(Environment.getExternalStorageDirectory() + "/ERP");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Demo")
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir("/ERP", file);

        mgr.enqueue(request);

        // Open Download Manager to view File progress
        Toast.makeText(getActivity(), "Downloading...",Toast.LENGTH_LONG).show();
      //  startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));

    }

    private void sendNotification() {

        Map<String,String> params=new HashMap<>();
        params.put("UserId",Url.UID);
        params.put("msgId",MsgId);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.ReadUpdate, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // Log.d("res", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

               // Toast.makeText(getContext(), "Server Error...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }

}
