package com.mipl.schoolerp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LoginActivity extends AppCompatActivity {


    private static final int PERMISSION_REQUEST_CODE = 200;

    TextInputEditText edtUsername, edtPass;
    Button btnLogin;
    TextView txtForgotPass, txtNotices;

    String isAlreadyLogin;

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sp = getApplicationContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();

        progressDialog = new ProgressDialog(this);

        permissions();

        edtUsername = findViewById(R.id.edtUsername);
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        txtForgotPass = findViewById(R.id.txtForgotPass);
        txtNotices = findViewById(R.id.txtNotices);

        isAlreadyLogin = String.valueOf(sp.getBoolean("loggedIn", false));

        if (isAlreadyLogin.equalsIgnoreCase("true")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        } else {

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String username = edtUsername.getText().toString();
                    String pass = edtPass.getText().toString();

                    if (username.isEmpty()) {
                        edtUsername.setError("Enter User Name...");
                        edtUsername.requestFocus();
                    } else if (pass.isEmpty()) {
                        edtPass.setError("Enter Pass...");
                        edtPass.requestFocus();
                    } else {
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setMessage("Loging...");
                        progressDialog.show();

                        Login();
                    }
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }

    public void Login() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("login_id", edtUsername.getText().toString());
        params.put("password", edtPass.getText().toString());

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.Login, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());

                try {
                    String success = response.getString("status");
                    String message = response.getString("message");

                    if (success.equalsIgnoreCase("1")) {

                        progressDialog.dismiss();

                        final JSONObject jsonObject = response.getJSONObject("UserDetails");

                        String id = jsonObject.getString("id");
                        String LoginId = jsonObject.getString("LoginId");
                        String name = jsonObject.getString("name");
                        String password = jsonObject.getString("password");
                        String Userid = jsonObject.getString("Userid");
                        String ErrorMessage = jsonObject.getString("ErrorMessage");
                        String role = jsonObject.getString("role");
                        String LoginStatus = jsonObject.getString("LoginStatus");
                        String Designation = jsonObject.getString("Designation");
                        String BranchName = jsonObject.getString("BranchName");
                        String isActive = jsonObject.getString("isActive");
                        String isDelete = jsonObject.getString("isDelete");
                        String message1 = jsonObject.getString("message");
                        String CreatedDate = jsonObject.getString("CreatedDate");
                        String UpdatedBy = jsonObject.getString("UpdatedBy");
                        String MobileNo = jsonObject.getString("MobileNo");
                        String DOB = jsonObject.getString("DOB");
                        String CreatedBy = jsonObject.getString("CreatedBy");
                        String Pan_details = jsonObject.getString("Pandetails");
                        String Adhar_Details = jsonObject.getString("AdharDetails");
                       // String Photo = jsonObject.getString("Photo");


                        String StandName=response.getString("StandName");
                        String StudnetDetail=response.getString("StudnetDetail");
                        String photo=response.getString("photo");
                        String roleNumber=response.getString("roleNumber");


                        editor.putBoolean("loggedIn", true);
                        editor.putString("LoginId", LoginId);
                        editor.putString("name", name);
                        editor.putString("role", role);
                        editor.putString("id", id);
                        editor.putString("Userid", Userid);
                        editor.putString("Photo", photo);
                        editor.putString("RoleNumber", roleNumber);
                        editor.putString("StandName", StandName);
                        editor.putString("StudnetDetail", StudnetDetail);


                        if (Userid.equalsIgnoreCase("0")) {
                            editor.putString("UID",id);
                        } else {
                            editor.putString("UID",Userid);
                        }

                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                    } else if (success.equalsIgnoreCase("2")) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();

                    } else if (success.equalsIgnoreCase("3")) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();

                    } else if (success.equalsIgnoreCase("4")) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Server Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                error.printStackTrace();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    private void permissions() {
        if (!checkPermission()) {
            requestPermission();
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {


                    boolean camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (camera && storage) {
                    } else {

                        if (Build.VERSION.SDK_INT >= 23) {

                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to All the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }

                            if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                                showMessageOKCancel("You need to allow access to All the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= 23) {
                                                    requestPermissions(new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }

                        }
                    }
                }

                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.app.AlertDialog.Builder(LoginActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .show();
    }


}
