package com.mipl.schoolerp.Fragments;


import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By Chaitanya.
 */

public class ViewDetailInboxView extends Fragment {

    TextView subject,date,fromName,msgBody,attachments,attachment1,attachment2,attachment3;
    LinearLayout view1,view2,view3;

    String Attach1,Attach2,Attach3,MsgId,File1,File2,File3;
    ImageView Reply;

    public ViewDetailInboxView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_view_detail_inbox_view, container, false);

        Bundle b = this.getArguments();
        String from=b.getString("From");
        String Subject=b.getString("Subject");
        String Body=b.getString("Body");
        String Date=b.getString("Date");

        Attach1=b.getString("attachment1");
        Attach2=b.getString("attachment2");
        Attach3=b.getString("attachment3");

        File1=b.getString("File1");
        File2=b.getString("File2");
        File3=b.getString("File3");

        MsgId=b.getString("msgId");

        subject=view.findViewById(R.id.subject);
        date=view.findViewById(R.id.date);
        fromName=view.findViewById(R.id.fromName);
        msgBody=view.findViewById(R.id.msgBody);
        attachments=view.findViewById(R.id.attachments);

        Reply=view.findViewById(R.id.Reply);

        view1=view.findViewById(R.id.view1);
        view2=view.findViewById(R.id.view2);
        view3=view.findViewById(R.id.view3);

        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);

        attachment1=view.findViewById(R.id.attachment1);
        attachment2=view.findViewById(R.id.attachment2);
        attachment3=view.findViewById(R.id.attachment3);



        subject.setText(Subject);
        date.setText(Date);
        fromName.setText(from);

        msgBody.setText(Html.fromHtml(Body).toString().trim().replace("\\s+", " "));


        if (!File1.equalsIgnoreCase("null")){
            attachment1.setText(Attach1);
            view1.setVisibility(View.VISIBLE);
        }

        if (!File2.equalsIgnoreCase("null")){
            attachment2.setText(Attach2);
            view2.setVisibility(View.VISIBLE);
        }

        if (!File3.equalsIgnoreCase("null")){
            attachment3.setText(Attach3);
            view3.setVisibility(View.VISIBLE);
        }



        attachment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(Url.IP + Attach1,File1);
            }
        });

        attachment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(Url.IP + Attach2,File2);
            }
        });

        attachment3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(Url.IP + Attach3,File3);
            }
        });



        Reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });




        sendNotification();

        return view;
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

}
