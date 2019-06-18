package com.mipl.schoolerp.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.mipl.schoolerp.Adapter.AnnualPlannerAdapter;
import com.mipl.schoolerp.Fragments.DashBoard;
import com.mipl.schoolerp.Fragments.DashboardStudent;
import com.mipl.schoolerp.Fragments.MessageCenter;
import com.mipl.schoolerp.Helper.AppController;
import com.mipl.schoolerp.Helper.CustomRequest;
import com.mipl.schoolerp.Model.AnnualPlannerModel;
import com.mipl.schoolerp.Model.SiblingLoginModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    boolean doubleBackToExitPressedOnce = false;

    ListView menulistview;
    private ArrayList<String> ListItems;
    private ArrayAdapter mStringAdaptor;

  //  private AdView adView;

    ArrayList<SiblingLoginModel> siblingList;


    String SLoginId;

    ImageView userImage;
    TextView username, email;


    private static final int PERMISSION_REQUEST_CODE = 200;

    public static final String SCHOOL_ERP = "SchoolERP";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String Sname,Sroll,SPhoto,SRoleNumber,LoginName,mainRoll,UserId;

    String Sname1,Spass1,SId1,Spassword1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissions();

        sp = getApplicationContext().getSharedPreferences(SCHOOL_ERP, Context.MODE_PRIVATE);
        editor = sp.edit();

        menulistview = findViewById(R.id.menuList);
        userImage = findViewById(R.id.SImage);
        username = findViewById(R.id.Sname);
        email = findViewById(R.id.Sclass);


        siblingList=new ArrayList<>();
        ListItems=new ArrayList<>();

        ListItems.add("Dashboard");
        ListItems.add("Logout");



        menulistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Class fragmentClass = null;
                Fragment fragment = null;

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                String name = parent.getItemAtPosition(position).toString();

                for (int i=0;i<siblingList.size();i++){

                    if (i==0){
                        Sname1 =siblingList.get(i).getName();
                        Spass1=siblingList.get(i).getPassword();
                        SId1=siblingList.get(i).getId();
                        Spassword1=siblingList.get(i).getPassword();
                    }
                }


                if (name.equalsIgnoreCase("Dashboard")) {
                    fragment = new DashBoard();
                }else if (name.equalsIgnoreCase("Logout")){

                    editor.clear();
                    editor.commit();

                    mStringAdaptor.notifyDataSetChanged();

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                }else if (name.equalsIgnoreCase(Sname1)){
                    Login();
                }

                if (fragment != null) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frameLayout, fragment);
                    ft.commit();
                }
                drawer.closeDrawer(GravityCompat.START);
            }
        });





       /* MobileAds.initialize(this, "ca-app-pub-9926084759991425~6327552399");
        adView = findViewById(R.id.ad_view);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        adView.loadAd(adRequest);*/




        Url.UID=sp.getString("UID","");
        Sname=sp.getString("StudnetDetail","");
        Sroll=sp.getString("StandName","");
        SPhoto=sp.getString("Photo","");
        SRoleNumber=sp.getString("RoleNumber","");
        LoginName=sp.getString("name","");
        UserId=sp.getString("Userid","");

        mainRoll=sp.getString("role","");

        SLoginId=sp.getString("id","");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();



        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);

        TextView name = hView.findViewById(R.id.Sname);
        TextView standerd = hView.findViewById(R.id.Sclass);
        final CircularImageView imageView = hView.findViewById(R.id.SImage);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/


        if (LoginName.equalsIgnoreCase("Admin")){
            username.setText(LoginName);
            email.setVisibility(View.INVISIBLE);
        }else {
            username.setText(Sname);
            email.setText(Sroll + "(Roll-no:-" +SRoleNumber+")");
        }


        if (SPhoto.isEmpty()){
            userImage.setBackgroundResource(R.drawable.stud);
        }else {

            Picasso.get().load(Url.IP+SPhoto).into(userImage, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    userImage.setBackgroundResource(R.drawable.stud);
                }
            });
        }

        getSiblingData();

        clearBackStackInclusive(getPackageName());

        if (mainRoll.equalsIgnoreCase("1")){
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new DashBoard()).commit();
            }
        }else if (mainRoll.equalsIgnoreCase("3")){
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new DashboardStudent()).commit();
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {

        /*if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
            }else {


                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                *//*MainActivity.this.finish();

                finishAndRemoveTask ();
                finish();
                finishAffinity();*//*

            }
        }*/



        if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START);
        } else {

            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
                getFragmentManager().popBackStack();
            } else if (!doubleBackToExitPressedOnce) {
                this.doubleBackToExitPressedOnce = true;

                Toast.makeText(getApplicationContext(), "Double click to exit.", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;

                    }
                }, 3000);
            } else {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

               /* finishAffinity();
                finish();*/
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Class fragmentClass = null;
        Fragment fragment = null;

        if (id == R.id.Dashboard) {

            fragment = new DashBoard();

        } else if (id == R.id.MsgCenter) {

            fragment = new MessageCenter();

        } else if (id == R.id.Logout) {

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

            editor.clear();
            editor.commit();


        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayout, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void clearBackStackInclusive(String tag) {
        getSupportFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
        new android.app.AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .show();
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static boolean checkURL(CharSequence input) {
        if (TextUtils.isEmpty(input)) {
            return false;
        }
        Pattern URL_PATTERN = Patterns.WEB_URL;
        boolean isURL = URL_PATTERN.matcher(input).matches();
        if (!isURL) {
            String urlString = input + "";
            if (URLUtil.isNetworkUrl(urlString)) {
                try {
                    new URL(urlString);
                    isURL = true;
                } catch (Exception e) {
                }
            }
        }
        return isURL;
    }


/*
    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }


    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void getSiblingData(){


        Map<String,String> params=new HashMap<>();
        params.put("UserId",SLoginId);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.SiblingDetails, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());

                try {

                    JSONArray jsonArray = response.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        final JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Id = jsonObject.getString("Id");
                        String Name = jsonObject.getString("Name");
                        String Role = jsonObject.getString("role");
                        String Password = jsonObject.getString("Password");

                        SiblingLoginModel siblingLoginModel=new SiblingLoginModel(Id,Name,Role,Password);
                        siblingList.add(siblingLoginModel);
                    }

                    mStringAdaptor = new ArrayAdapter<String>(MainActivity.this, R.layout.layout_itm_list, ListItems);
                    menulistview.setAdapter(mStringAdaptor);

                    if (siblingList.size()>0){

                        for (int i=0;i<siblingList.size();i++){

                            ListItems.add(siblingList.get(i).getName());

                            mStringAdaptor = new ArrayAdapter<String>(MainActivity.this, R.layout.layout_itm_list, ListItems);
                            menulistview.setAdapter(mStringAdaptor);
                        }
                    }else {
                        mStringAdaptor = new ArrayAdapter<String>(MainActivity.this, R.layout.layout_itm_list, ListItems);
                        menulistview.setAdapter(mStringAdaptor);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "Server Error...", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);


    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public void Login() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("login_id",SId1);
        params.put("password",Spassword1);

        CustomRequest jsonObjRequest = new CustomRequest(Request.Method.POST, Url.Login, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("res", response.toString());

                try {
                    String success = response.getString("status");
                    String message = response.getString("message");

                    if (success.equalsIgnoreCase("1")) {

                        editor.clear();
                        editor.commit();


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

                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);


                    } else if (success.equalsIgnoreCase("2")) {

                        Toast.makeText(MainActivity.this, "" + message, Toast.LENGTH_SHORT).show();

                    } else if (success.equalsIgnoreCase("3")) {
                        Toast.makeText(MainActivity.this, "" + message, Toast.LENGTH_SHORT).show();

                    } else if (success.equalsIgnoreCase("4")) {
                        Toast.makeText(MainActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Server Error....", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjRequest);
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
