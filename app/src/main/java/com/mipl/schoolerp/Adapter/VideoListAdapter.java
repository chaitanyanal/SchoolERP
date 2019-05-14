package com.mipl.schoolerp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipl.schoolerp.Fragments.FullIImageView;
import com.mipl.schoolerp.Fragments.FullVideoView;
import com.mipl.schoolerp.Model.VideoGalleryModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyViewHolder> {

    Context context;
    ArrayList<VideoGalleryModel> galleryModelArrayList;

    public VideoListAdapter(Context context, ArrayList<VideoGalleryModel> galleryModelArrayList) {
        this.context = context;
        this.galleryModelArrayList = galleryModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.videolist_layout, viewGroup, false);
        return new VideoListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        VideoGalleryModel videoGalleryModel=galleryModelArrayList.get(i);
        myViewHolder.textView.setText(videoGalleryModel.getVideoDetails());

    }

    @Override
    public int getItemCount() {
        return galleryModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.videoName);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i=getAdapterPosition();

                    String aa=galleryModelArrayList.get(i).getVideoLink();


                    Bundle bundle=new Bundle();
                    bundle.putString("link",aa.substring(aa.lastIndexOf("/") + 1));

                    Fragment fragment=new FullVideoView();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });
        }
    }
}
