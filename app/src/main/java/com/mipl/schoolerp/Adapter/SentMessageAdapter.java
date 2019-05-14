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
import android.widget.ImageView;
import android.widget.TextView;

import com.mipl.schoolerp.Fragments.ViewDetailInboxView;
import com.mipl.schoolerp.Fragments.ViewDetailSentView;
import com.mipl.schoolerp.Model.SentViewModel;
import com.mipl.schoolerp.R;

import java.util.ArrayList;

public class SentMessageAdapter extends RecyclerView.Adapter<SentMessageAdapter.MyViewHolder> {

    Context context;
    ArrayList<SentViewModel> sentViewModelArrayList;

    public SentMessageAdapter(Context context, ArrayList<SentViewModel> sentViewModelArrayList) {
        this.context = context;
        this.sentViewModelArrayList = sentViewModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sent_tab_layout, viewGroup, false);
        return new SentMessageAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        SentViewModel sentViewModel=sentViewModelArrayList.get(i);

        myViewHolder.txtSubject.setText(sentViewModel.getSubject());
        myViewHolder.txtUserName.setText(sentViewModel.getTolist());
    }

    @Override
    public int getItemCount() {
        return sentViewModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtUserName,txtSubject;
        ImageView ckImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName=itemView.findViewById(R.id.sent_txtUserName);
            txtSubject=itemView.findViewById(R.id.sent_txtview);
            ckImg=itemView.findViewById(R.id.sent_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i=getAdapterPosition();

                    Bundle bundle=new Bundle();
                    bundle.putString("To",sentViewModelArrayList.get(i).getTolist());
                    bundle.putString("Subject",sentViewModelArrayList.get(i).getSubject());
                    bundle.putString("Body",sentViewModelArrayList.get(i).getMsgbody());
                    bundle.putString("Date",sentViewModelArrayList.get(i).getDate());

                    bundle.putString("Attachment1",sentViewModelArrayList.get(i).getAttachment1());
                    bundle.putString("Attachment2",sentViewModelArrayList.get(i).getAttachment2());
                    bundle.putString("Attachment3",sentViewModelArrayList.get(i).getAttachment3());

                    bundle.putString("File1",sentViewModelArrayList.get(i).getFile1());
                    bundle.putString("File2",sentViewModelArrayList.get(i).getFile2());
                    bundle.putString("File3",sentViewModelArrayList.get(i).getFile3());

                    bundle.putString("msgId",sentViewModelArrayList.get(i).getId());



                    Fragment fragment = new ViewDetailSentView();
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
