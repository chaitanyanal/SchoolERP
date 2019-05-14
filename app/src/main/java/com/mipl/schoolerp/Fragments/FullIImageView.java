package com.mipl.schoolerp.Fragments;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullIImageView extends Fragment {

    ImageView selectedImage;;
    private Bundle bundle;


    public FullIImageView() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_full_iimage_view, container, false);

        selectedImage = (ImageView)view.findViewById(R.id.selectedImage); 

        Bundle bundle= this.getArguments();
        String aa=bundle.getString("photo");

        Picasso.get().load(aa).into(selectedImage);

        return view;
    }

}
