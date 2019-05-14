package com.mipl.schoolerp.Adapter;



import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mipl.schoolerp.Fragments.AnnualPlannerDetailsPage;
import com.mipl.schoolerp.Fragments.FullIImageView;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import android.support.v4.app.Fragment;

public class PhotoGallaryAdapter  extends BaseAdapter {

    Context context;
    ArrayList<String> logos;
    LayoutInflater inflter;

    public PhotoGallaryAdapter(Context applicationContext, ArrayList<String> logos) {
        this.context = applicationContext;
        this.logos = logos;
        inflter = (LayoutInflater.from(applicationContext));

    }

    @Override
    public int getCount() {
        return logos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.gallery_view_layout, null);
        ImageView icon = view.findViewById(R.id.icon);

        Picasso.get().load(Url.IP+"/"+logos.get(i)).into(icon);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Log.d("aaa", String.valueOf(i));

                Bundle bundle=new Bundle();
                bundle.putString("photo",Url.IP+"/"+logos.get(i));

                Fragment fragment=new FullIImageView();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return view;
    }
}
