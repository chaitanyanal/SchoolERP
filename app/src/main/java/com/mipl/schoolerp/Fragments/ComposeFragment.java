package com.mipl.schoolerp.Fragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mipl.schoolerp.Activity.MainActivity;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Helper.FilePath;
import com.mipl.schoolerp.Helper.VolleyMultipartRequest;
import com.mipl.schoolerp.Model.SendDetailsModule;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.media.MediaRecorder.VideoSource.CAMERA;
import static com.mipl.schoolerp.Helper.FilePath.getPath;

/**
 * A simple {@link Fragment} subclass.
 */

public class ComposeFragment extends Fragment {

    Button btnAdd, btnSend, btnBack, btnAddCC;
    EditText toMail, edtSubject, edtContent, toMailCC;
    TextView Finename1, Finename2, Finename3;
    ImageView attachmentFirst, attachmentSecond, attachmentThird;
    String UserId, name, role, LoginId, id;
    String path;
    int chooserFirst = 0;
    Bitmap imageFirst, imageSecond, imageThird;
    private Uri filePath, filePath2, filePath3;
    String MessageId;

    String Image1,Image2,Image3;

    Context context;


    ProgressDialog progressDialog;

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    String First;


    public ComposeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compose, container, false);


        sp = getContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();


        context=getActivity();

        progressDialog=new ProgressDialog(getContext());

        btnAdd = view.findViewById(R.id.btnAdd);
        btnAddCC = view.findViewById(R.id.btnAddCC);
        btnSend = view.findViewById(R.id.btnSend);
        btnBack = view.findViewById(R.id.btnBack);
        toMail = view.findViewById(R.id.toMail);
        toMailCC = view.findViewById(R.id.toMailCC);
        edtSubject = view.findViewById(R.id.edtSubject);
        edtContent = view.findViewById(R.id.edtContent);

        Finename1 = view.findViewById(R.id.Finename1);
        attachmentFirst = view.findViewById(R.id.attachmentFirst);

        Finename2 = view.findViewById(R.id.Finename2);
        attachmentSecond = view.findViewById(R.id.attachmentSecond);

        Finename3 = view.findViewById(R.id.Finename3);
        attachmentThird = view.findViewById(R.id.attachmentThird);


        UserId = sp.getString("Userid", "");
        name = sp.getString("name", "");
        role = sp.getString("role", "");
        LoginId = sp.getString("LoginId", "");
        id = sp.getString("id", "");

        Log.d("id", id);
        Log.d("UserId", UserId);


        toMail.setClickable(true);
        toMail.setEnabled(true);
        toMail.setFocusable(false);

        toMailCC.setClickable(true);
        toMailCC.setEnabled(true);
        toMailCC.setFocusable(false);


        edtSubject.clearFocus();
        edtContent.clearFocus();


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        toMail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.toMail) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        toMailCC.setScroller(new Scroller(getContext()));
        toMailCC.setVerticalScrollBarEnabled(true);
        toMailCC.setMovementMethod(new ScrollingMovementMethod());


        toMailCC.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.toMailCC) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        toMailCC.setScroller(new Scroller(getContext()));
        toMailCC.setVerticalScrollBarEnabled(true);
        toMailCC.setMovementMethod(new ScrollingMovementMethod());


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("Sub",edtSubject.getText().toString());
                editor.putString("Cont",edtContent.getText().toString());

                editor.commit();

                Fragment fragment = new ComposeMailTo();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        attachmentFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                editor.putString("Sub",edtSubject.getText().toString());
                editor.putString("Cont",edtContent.getText().toString());

                editor.commit();


                try {
                    chooserFirst = 1;
                    showPictureDialog();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        attachmentSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("Sub",edtSubject.getText().toString());
                editor.putString("Cont",edtContent.getText().toString());

                editor.commit();


                try {
                    chooserFirst = 2;
                    showPictureDialog();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        attachmentThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("Sub",edtSubject.getText().toString());
                editor.putString("Cont",edtContent.getText().toString());

                editor.commit();


                try {
                    chooserFirst = 3;
                    showPictureDialog();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        btnAddCC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("Sub",edtSubject.getText().toString());
                editor.putString("Cont",edtContent.getText().toString());

                editor.commit();


                Fragment fragment = new ComposeMailCc();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                if (toMail.getText().toString().isEmpty()) {

                    toMail.requestFocus();
                    toMail.setFocusable(true);
                    toMail.setError("Add Receiptant...");

                } else if (edtSubject.getText().toString().isEmpty()) {

                    edtSubject.requestFocus();
                    edtSubject.setFocusable(true);
                    edtSubject.setError("Add Subject...");

                } else if (edtContent.getText().toString().isEmpty()) {

                    edtContent.requestFocus();
                    edtContent.setFocusable(true);
                    edtContent.setError("Enter Message Body...");

                } else {

                    progressDialog.setTitle("Submiting..");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();


                    Map<String, String> params = new HashMap<>();

                    params.put("messagebody", edtContent.getText().toString());
                    params.put("Subject", edtSubject.getText().toString());
                    params.put("Role", role);

                    if (UserId.equalsIgnoreCase("0")) {
                        params.put("Userid", id);
                    } else {
                        params.put("Userid", UserId);
                    }

                    for (int i = 0; i < Url.ArrayListOfDetails.size(); i++) {

                        SendDetailsModule detailsModule=Url.ArrayListOfDetails.get(i);

                        if (detailsModule.getId().equalsIgnoreCase(Url.PrincipalToId)){
                            params.put("list[" + (i) + "].AdminTo", Url.PrincipalToId);
                            params.put("list[" + (i) + "].RoleAdmin", Url.PrincipalToRole);

                           // Log.d("aato", Url.PrincipalToId);
                           // Log.d("bbto", Url.PrincipalToRole);

                        }else {
                            params.put("list[" + (i) + "].ToId", Url.ArrayListOfDetails.get(i).getId());
                            params.put("list[" + (i) + "].ToRole", Url.ArrayListOfDetails.get(i).getRole());
                        }
                    }


                    for (int i = 0; i < Url.ArrayListOfDetailsCc.size(); i++) {

                        SendDetailsModule detailsModule=Url.ArrayListOfDetailsCc.get(i);

                        if (detailsModule.getId().equalsIgnoreCase(Url.PrincipalToId)){
                            params.put("list[" + (i) + "].CCAdminTo", Url.PrincipalToIdCC);
                            params.put("list[" + (i) + "].RoleCCAdmin", Url.PrincipalToRoleCC);
                        }else {
                            params.put("list[" + (i) + "].CCToId", Url.ArrayListOfDetailsCc.get(i).getId());
                            params.put("list[" + (i) + "].CCRole", Url.ArrayListOfDetailsCc.get(i).getRole());
                        }


                       //  Log.d("aacc", Url.ArrayListOfDetails.get(i).getId());
                       //  Log.d("bbcc", Url.ArrayListOfDetails.get(i).getRole());
                    }


                    Log.d("UserId", UserId);
                    Log.d("role", role);
                    Log.d("content", edtContent.getText().toString());
                    Log.d("subject", edtSubject.getText().toString());


                    CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.ComposeMessage, params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("res", response.toString());

                            try {
                                String status = response.getString("status");

                                MessageId = response.getString("MessageId");

                                Log.d("MessageId", MessageId);

                                if (!Finename1.getText().toString().isEmpty()) {
                                    uploadMultipart();
                                }

                                if (!Finename2.getText().toString().isEmpty()) {
                                    uploadMultipart();
                                }

                                if (!Finename3.getText().toString().isEmpty()) {
                                    uploadMultipart();
                                }

                                if (!Finename1.getText().toString().isEmpty()) {
                                    progressDialog.setTitle("Submiting..");
                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    progressDialog.show();
                                    uploadBitmapFirst(Image1);
                                }

                                if (!Finename2.getText().toString().isEmpty()) {
                                    progressDialog.setTitle("Submiting..");
                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    progressDialog.show();
                                    uploadBitmapSecond(Image2);
                                }

                                if (!Finename3.getText().toString().isEmpty()) {
                                    progressDialog.setTitle("Submiting..");
                                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                    progressDialog.show();
                                    uploadBitmapThird(Image3);
                                }

                                Toast.makeText(getContext(), "" + status, Toast.LENGTH_SHORT).show();


                                progressDialog.dismiss();
                                progressDialog.hide();

                                toMail.setText("");
                                toMailCC.setText("");
                                edtSubject.setText("");
                                edtContent.setText("");

                                Url.ArrayListOfDetails.clear();
                                Url.ArrayListOfDetailsCc.clear();

                                Fragment fragment = new DashBoard();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayout, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            progressDialog.hide();
                            Toast.makeText(getContext(), "Server Error...", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    });

                    AppController.getInstance().addToRequestQueue(jsonObjRequest);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new DashBoard();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        try {
            if (Url.ArrayListOfDetails.size()>0){
                StringBuilder builder = new StringBuilder();
                for (SendDetailsModule details : Url.ArrayListOfDetails) {
                    builder.append(details.getName() + ";");
                }
                String aaa = builder.toString();
                toMail.setText(aaa);
                toMail.setTextSize(12);
            }else {
                toMail.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            StringBuilder builder = new StringBuilder();
            for (SendDetailsModule details : Url.ArrayListOfDetailsCc) {
                builder.append(details.getName() + ";");
            }

            if (Url.ArrayListOfDetailsCc.size() == 0) {
                toMailCC.setText("");
            } else {
                String aaa = builder.toString();
                toMailCC.setText(aaa);
                toMailCC.setTextSize(12);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Select Pdf"
        };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                selectPdf();
                                //  takePhotoFromCamera();
                                break;
                            case 2:
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {

        try {

           /* Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), chooserFirst);*/


            /*Intent galleryIntent = new Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, chooserFirst);*/

            /*Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, chooserFirst);*/


            String path = Environment.getExternalStorageDirectory() + "/images/imagename.jpg";
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, chooserFirst);


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        ((Activity) getContext()).startActivityForResult(intent, CAMERA);
    }

    private void selectPdf() {

        try {
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Pdf"), chooserFirst);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void browseDocuments() {

        String[] mimeTypes = {"application/msword", "text/plain", "application/pdf", "application/zip"};
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }

        } else {

            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), 100);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }

        try {

            if (requestCode == 1) {

                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri contentURI = data.getData();

                        String mimeType = getContext().getContentResolver().getType(contentURI);
                        Log.d("test", mimeType);

                        Log.d("uri", String.valueOf(contentURI));


                        if (mimeType.equalsIgnoreCase("text/plain")) {

                        } else if (mimeType.equalsIgnoreCase("application/pdf")) {

                            filePath = data.getData();

                            Log.d("pdf name", String.valueOf(filePath));

                            String fpath= String.valueOf(filePath);

                            String filename=fpath.substring(fpath.lastIndexOf("/")+1)+".pdf";
                            Finename1.setText(filename);


                            First= data.getData().getPath();

                        } else if (mimeType.equalsIgnoreCase("application/vnd.ms-powerpoint")) {

                        } else if (mimeType.equalsIgnoreCase("application/msword")) {

                        } else if (mimeType.equalsIgnoreCase("application/vnd.ms-excel")) {

                            File file = new File(contentURI.getPath().toString());
                            Finename1.setText(file.getName().toString());

                            StringTokenizer tokens = new StringTokenizer(file.getName().toString(), ":");
                            String first = tokens.nextToken();
                            String file_1 = tokens.nextToken().trim();


                        } else if (mimeType.equalsIgnoreCase("application/x-wav")) {

                        } else if (mimeType.equalsIgnoreCase("application/rtf")) {

                        } else if (mimeType.equalsIgnoreCase("image/gif")) {

                        } else if (mimeType.equalsIgnoreCase("image/jpg")) {


                            filePath = data.getData();

                            String fpath= String.valueOf(filePath);


                            final InputStream imageStream = context.getContentResolver().openInputStream(contentURI);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            String encodedImage = encodeImage(selectedImage);

                            Log.d("encoded",encodedImage);

                            Image1=encodedImage;

                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            Log.d("decodedByte", String.valueOf(decodedByte));

                            imageFirst = decodedByte;

                            Bitmap emptyBitmap = Bitmap.createBitmap(decodedByte.getWidth(), decodedByte.getHeight(), decodedByte.getConfig());

                            if (decodedByte.sameAs(emptyBitmap)) {
                                Log.d("empty","empty");
                            }else {
                                String filename=fpath.substring(fpath.lastIndexOf("/")+1)+".jpg";
                                Finename1.setText(filename);
                            }


                        } else if (mimeType.equalsIgnoreCase("image/jpeg")) {


                            filePath = data.getData();

                            String fpath= String.valueOf(filePath);

                            final InputStream imageStream = context.getContentResolver().openInputStream(contentURI);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            String encodedImage = encodeImage(selectedImage);

                            Log.d("encoded",encodedImage);

                            Image1=encodedImage;

                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            Log.d("decodedByte", String.valueOf(decodedByte));

                            imageFirst = decodedByte;


                          //  postImage(encodedImage);


                            Bitmap emptyBitmap = Bitmap.createBitmap(decodedByte.getWidth(), decodedByte.getHeight(), decodedByte.getConfig());

                            if (decodedByte.sameAs(emptyBitmap)) {
                                Log.d("empty","empty");
                            }else {
                                String filename=fpath.substring(fpath.lastIndexOf("/")+1)+".jpeg";
                                Finename1.setText(String.valueOf(filename));
                            }





                        } else if (mimeType.equalsIgnoreCase("image/png")) {

                            Uri uri=data.getData();

                            filePath = data.getData();
                            String fpath= String.valueOf(filePath);


                            final InputStream imageStream = context.getContentResolver().openInputStream(uri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            String encodedImage = encodeImage(selectedImage);

                            Log.d("encoded",encodedImage);

                            Image1=encodedImage;

                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            Log.d("decodedByte", String.valueOf(decodedByte));

                            imageFirst = decodedByte;

                            Bitmap emptyBitmap = Bitmap.createBitmap(decodedByte.getWidth(), decodedByte.getHeight(), decodedByte.getConfig());

                            if (decodedByte.sameAs(emptyBitmap)) {
                                Log.d("empty","empty");
                            }else {
                                String filename=fpath.substring(fpath.lastIndexOf("/")+1)+".png";
                                Finename1.setText(String.valueOf(filename));
                            }

                        } else {
                            Toast.makeText(getContext(), "Not Supported File Format...", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (requestCode == CAMERA) {
                    final Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();

                    path = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    Log.d("imageString", path);

                }
            } else if (requestCode == 2) {

                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri contentURI = data.getData();

                        Uri returnUri = data.getData();
                        String mimeType = getContext().getContentResolver().getType(returnUri);

                        Log.d("test", mimeType);

                        if (mimeType.equalsIgnoreCase("text/plain")) {

                        } else if (mimeType.equalsIgnoreCase("application/pdf")) {

                            filePath2 = data.getData();

                            Log.d("pdf name", String.valueOf(filePath2));

                            String fpath= String.valueOf(filePath2);

                            String filename=fpath.substring(fpath.lastIndexOf("/")+1)+".pdf";

                            Finename2.setText(filename);

                        } else if (mimeType.equalsIgnoreCase("application/vnd.ms-powerpoint")) {

                        } else if (mimeType.equalsIgnoreCase("application/msword")) {

                        } else if (mimeType.equalsIgnoreCase("application/vnd.ms-excel")) {

                        } else if (mimeType.equalsIgnoreCase("application/x-wav")) {

                        } else if (mimeType.equalsIgnoreCase("application/rtf")) {

                        } else if (mimeType.equalsIgnoreCase("image/gif")) {

                        } else if (mimeType.equalsIgnoreCase("image/jpg")) {

                            filePath2 = data.getData();

                            String fpath= String.valueOf(filePath);

                            final InputStream imageStream = context.getContentResolver().openInputStream(contentURI);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            String encodedImage = encodeImage(selectedImage);

                            Log.d("encoded",encodedImage);

                            Image2=encodedImage;

                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            Log.d("decodedByte", String.valueOf(decodedByte));

                            imageSecond = decodedByte;

                            Bitmap emptyBitmap = Bitmap.createBitmap(decodedByte.getWidth(), decodedByte.getHeight(), decodedByte.getConfig());

                            if (decodedByte.sameAs(emptyBitmap)) {
                                Log.d("empty","empty");
                            }else {
                                String filename=fpath.substring(fpath.lastIndexOf("/")+1)+".jpg";
                                Finename2.setText(filename);
                            }

                        } else if (mimeType.equalsIgnoreCase("image/jpeg")) {

                            filePath2 = data.getData();

                            String fpath= String.valueOf(filePath2);

                            final InputStream imageStream = context.getContentResolver().openInputStream(contentURI);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            String encodedImage = encodeImage(selectedImage);

                            Log.d("encoded",encodedImage);

                            Image2=encodedImage;

                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            Log.d("decodedByte", String.valueOf(decodedByte));

                            imageSecond = decodedByte;

                            Bitmap emptyBitmap = Bitmap.createBitmap(decodedByte.getWidth(), decodedByte.getHeight(), decodedByte.getConfig());

                            if (decodedByte.sameAs(emptyBitmap)) {
                                Log.d("empty","empty");
                            }else {
                                String filename=fpath.substring(fpath.lastIndexOf("/")+1)+".jpeg";
                                Finename2.setText(String.valueOf(filename));
                            }

                        } else if (mimeType.equalsIgnoreCase("image/png")) {

                            Uri uri=data.getData();

                            filePath2 = data.getData();
                            String fpath= String.valueOf(filePath2);


                            final InputStream imageStream = context.getContentResolver().openInputStream(uri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            String encodedImage = encodeImage(selectedImage);

                            Log.d("encoded",encodedImage);

                            Image2=encodedImage;

                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            Log.d("decodedByte", String.valueOf(decodedByte));

                            imageSecond = decodedByte;

                            Bitmap emptyBitmap = Bitmap.createBitmap(decodedByte.getWidth(), decodedByte.getHeight(), decodedByte.getConfig());

                            if (decodedByte.sameAs(emptyBitmap)) {
                                Log.d("empty","empty");
                            }else {
                                String filename=fpath.substring(fpath.lastIndexOf("/")+1)+".png";
                                Finename2.setText(String.valueOf(filename));
                            }
                        } else {
                            Toast.makeText(getContext(), "Not Supported File Format...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            } else if (requestCode == 3) {

                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        Uri contentURI = data.getData();

                        Uri returnUri = data.getData();
                        String mimeType = getContext().getContentResolver().getType(returnUri);

                        Log.d("test", mimeType);

                        if (mimeType.equalsIgnoreCase("text/plain")) {

                        } else if (mimeType.equalsIgnoreCase("application/pdf")) {

                            filePath3 = data.getData();
                            Log.d("pdf name", String.valueOf(filePath3));

                            String fpath= String.valueOf(filePath3);
                            String filename=fpath.substring(fpath.lastIndexOf("/")+1)+".pdf";

                            Finename3.setText(filename);

                        } else if (mimeType.equalsIgnoreCase("application/vnd.ms-powerpoint")) {

                        } else if (mimeType.equalsIgnoreCase("application/msword")) {

                        } else if (mimeType.equalsIgnoreCase("application/vnd.ms-excel")) {

                        } else if (mimeType.equalsIgnoreCase("application/x-wav")) {

                        } else if (mimeType.equalsIgnoreCase("application/rtf")) {

                        } else if (mimeType.equalsIgnoreCase("image/gif")) {

                        } else if (mimeType.equalsIgnoreCase("image/jpg")) {

                            filePath3 = data.getData();

                            String fpath= String.valueOf(filePath3);

                            final InputStream imageStream = context.getContentResolver().openInputStream(contentURI);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            String encodedImage = encodeImage(selectedImage);

                            Log.d("encoded",encodedImage);

                            Image3=encodedImage;

                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            Log.d("decodedByte", String.valueOf(decodedByte));

                            imageThird = decodedByte;

                            Bitmap emptyBitmap = Bitmap.createBitmap(decodedByte.getWidth(), decodedByte.getHeight(), decodedByte.getConfig());

                            if (decodedByte.sameAs(emptyBitmap)) {
                                Log.d("empty","empty");
                            }else {
                                String filename=fpath.substring(fpath.lastIndexOf("/")+1)+".jpg";
                                Finename3.setText(filename);
                            }

                        } else if (mimeType.equalsIgnoreCase("image/jpeg")) {

                            filePath3 = data.getData();

                            String fpath= String.valueOf(filePath3);

                            final InputStream imageStream = context.getContentResolver().openInputStream(contentURI);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            String encodedImage = encodeImage(selectedImage);

                            Log.d("encoded",encodedImage);

                            Image3=encodedImage;

                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            Log.d("decodedByte", String.valueOf(decodedByte));

                            imageThird = decodedByte;

                            Bitmap emptyBitmap = Bitmap.createBitmap(decodedByte.getWidth(), decodedByte.getHeight(), decodedByte.getConfig());

                            if (decodedByte.sameAs(emptyBitmap)) {
                                Log.d("empty","empty");
                            }else {
                                String filename=fpath.substring(fpath.lastIndexOf("/")+1)+".jpeg";
                                Finename3.setText(String.valueOf(filename));
                            }


                        } else if (mimeType.equalsIgnoreCase("image/png")) {

                            filePath3 = data.getData();

                            String fpath= String.valueOf(filePath3);

                            final InputStream imageStream = context.getContentResolver().openInputStream(contentURI);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            String encodedImage = encodeImage(selectedImage);

                            Log.d("encoded",encodedImage);

                            Image3=encodedImage;

                            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            Log.d("decodedByte", String.valueOf(decodedByte));

                            imageThird = decodedByte;

                            Bitmap emptyBitmap = Bitmap.createBitmap(decodedByte.getWidth(), decodedByte.getHeight(), decodedByte.getConfig());

                            if (decodedByte.sameAs(emptyBitmap)) {
                                Log.d("empty","empty");
                            }else {
                                String filename=fpath.substring(fpath.lastIndexOf("/")+1)+".png";
                                Finename3.setText(String.valueOf(filename));
                            }

                        } else {
                            Toast.makeText(getContext(), "Not Supported File Format...", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContext().getContentResolver() != null) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }


    public String getRealPathFromURI1(Uri contentUri) {

        String aa="";

        String[] proj = { MediaStore.Images.Media.DATA };

        //This method was deprecated in API level 11
        //Cursor cursor = managedQuery(contentUri, proj, null, null, null);

        CursorLoader cursorLoader = new CursorLoader(getContext(),contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        aa= cursor.getString(column_index);
        return aa;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void uploadMultipart() {

        String path = null, path1 = null, path2 = null;
        String Uid;

        if (UserId.equalsIgnoreCase("0")) {
            Uid = id;
        } else {
            Uid = UserId;
        }


        try{
            if (filePath != null) {
                path = FilePath.getPath(getContext(),filePath);
            } else if (filePath2 != null) {
                path1 = FilePath.getPath(getContext(),filePath2);
            } else if (filePath3 != null) {
                path2 = FilePath.getPath(getContext(),filePath3);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        if (path == null) {
            // Toast.makeText(getContext(), "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        } else {
            try {
                String uploadId = UUID.randomUUID().toString();

                new MultipartUploadRequest(getContext(), uploadId, Url.Attachment1)
                        .addFileToUpload(path, "fileDiff") //Adding file
                        .addParameter("Userid", Uid)
                        .addParameter("msgId", MessageId)
                        .addParameter("Role", role)
                        .addParameter("fileNm", "test")
                        .setMaxRetries(5)
                        .startUpload(); //Starting the upload

            } catch (Exception exc) {
              //  Toast.makeText(getContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        if (path1 == null) {
            //  Toast.makeText(getContext(), "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        } else {
            try {
                String uploadId = UUID.randomUUID().toString();

                new MultipartUploadRequest(getContext(), uploadId, Url.Attachment2)
                        .addFileToUpload(path1, "fileDiff") //Adding file
                        .addParameter("Userid", Uid)
                        .addParameter("msgId", MessageId)
                        .addParameter("Role", role)
                        .addParameter("fileNm", "test")
                       // .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(5)
                        .startUpload(); //Starting the upload

            } catch (Exception exc) {
               // Toast.makeText(getContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        if (path2 == null) {
            //  Toast.makeText(getContext(), "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        } else {
            try {
                String uploadId = UUID.randomUUID().toString();

                new MultipartUploadRequest(getContext(), uploadId, Url.Attachment3)
                        .addFileToUpload(path2, "fileDiff") //Adding file
                        .addParameter("Userid", Uid)
                        .addParameter("msgId", MessageId)
                        .addParameter("Role", role)
                        .addParameter("fileNm", "test")
                       // .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(5)
                        .startUpload(); //Starting the upload

            } catch (Exception exc) {
              //  Toast.makeText(getContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }




    private void uploadBitmapFirst(final String imagePath) {

        if (imagePath!=null){

            final String Uid;

            if (UserId.equalsIgnoreCase("0")) {
                Uid = id;
            } else {
                Uid = UserId;
            }

            //our custom volley request
           /* VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Url.Attachment1,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {

                            try {

                                progressDialog.dismiss();
                                progressDialog.hide();

                                JSONObject obj = new JSONObject(new String(response.data));
                                Log.d("response", String.valueOf(obj));

                                String success = obj.getString("status");

                                Log.d("success", success);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //  Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Userid", Uid);
                    params.put("msgId", MessageId);
                    params.put("Role", role);
                    params.put("file", bitmap);
                    return params;
                }

                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();
                  //  params.put("file", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                    return params;
                }
            };

            //adding the request to volley
            Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);*/


           Map<String,String> params=new HashMap<>();
           params.put("Userid",Uid);
           params.put("msgId", MessageId);
           params.put("Role", role);
           params.put("file", imagePath);
           params.put("fileNm", Finename1.getText().toString());



            CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.Attachment1, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.d("res", response.toString());

                    try {
                        String status = response.getString("status");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Server Error...", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            });

            AppController.getInstance().addToRequestQueue(jsonObjRequest);

        }
    }

    private void uploadBitmapSecond(final String imagePath1) {

        if (imagePath1!=null){

            final String Uid;

            if (UserId.equalsIgnoreCase("0")) {
                Uid = id;
            } else {
                Uid = UserId;
            }

            Map<String,String> params=new HashMap<>();
            params.put("Userid",Uid);
            params.put("msgId", MessageId);
            params.put("Role", role);
            params.put("file", imagePath1);
            params.put("fileNm", Finename1.getText().toString());



            CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.Attachment2, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.d("res", response.toString());

                    try {
                        String status = response.getString("status");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Server Error...", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            });

            AppController.getInstance().addToRequestQueue(jsonObjRequest);
        }


    }

    private void uploadBitmapThird(final String imagePath2) {


        if (imagePath2!=null){

            final String Uid;

            if (UserId.equalsIgnoreCase("0")) {
                Uid = id;
            } else {
                Uid = UserId;
            }

            //our custom volley request
            Map<String,String> params=new HashMap<>();
            params.put("Userid",Uid);
            params.put("msgId", MessageId);
            params.put("Role", role);
            params.put("file", imagePath2);
            params.put("fileNm", Finename1.getText().toString());



            CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.Attachment3, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.d("res", response.toString());

                    try {
                        String status = response.getString("status");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Server Error...", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            });

            AppController.getInstance().addToRequestQueue(jsonObjRequest);

        }
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    @Override
    public void onResume() {
        super.onResume();


        String aa=sp.getString("Sub","");
        String bb=sp.getString("Cont","");
        edtSubject.setText(aa);
        edtContent.setText(bb);




        try {
            if (Url.ArrayListOfDetails.size()>0){
                StringBuilder builder = new StringBuilder();
                for (SendDetailsModule details : Url.ArrayListOfDetails) {
                    builder.append(details.getName() + ";");
                }
                String aaa = builder.toString();
                toMail.setText(aaa);
                toMail.setTextSize(12);
            }else {
                toMail.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            StringBuilder builder = new StringBuilder();
            for (SendDetailsModule details : Url.ArrayListOfDetailsCc) {
                builder.append(details.getName() + ";");
            }

            if (Url.ArrayListOfDetailsCc.size() == 0) {
                toMailCC.setText("");
            } else {
                String aaa = builder.toString();
                toMailCC.setText(aaa);
                toMailCC.setTextSize(12);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((MainActivity)getActivity()).clearBackStackInclusive(getActivity().getPackageName());
    }


    @Override
    public void onPause() {
        super.onPause();


        try {
            if (Url.ArrayListOfDetails.size()>0){
                StringBuilder builder = new StringBuilder();
                for (SendDetailsModule details : Url.ArrayListOfDetails) {
                    builder.append(details.getName() + ";");
                }
                String aaa = builder.toString();
                toMail.setText(aaa);
                toMail.setTextSize(12);
            }else {
                toMail.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        try {
            StringBuilder builder = new StringBuilder();
            for (SendDetailsModule details : Url.ArrayListOfDetailsCc) {
                builder.append(details.getName() + ";");
            }

            if (Url.ArrayListOfDetailsCc.size() == 0) {
                toMailCC.setText("");
            } else {
                String aaa = builder.toString();
                toMailCC.setText(aaa);
                toMailCC.setTextSize(12);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPathAA(Context context, Uri uri) throws URISyntaxException {
        String path="";

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            path= uri.getPath();
            return path;
        }

        return null;
    }





    public String getPath1(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public static Bitmap decodeSampledBitmapFromResource(String resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
// Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 2;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }









    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,80,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }


    private void postImage(String encodedImage) {

        HashMap<String,String> params=new HashMap<>();
        params.put("image",encodedImage);


        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, "http://192.168.1.192:2253/WebServiceERpSchool/TestBase64Image", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Server Error...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);

    }

}
