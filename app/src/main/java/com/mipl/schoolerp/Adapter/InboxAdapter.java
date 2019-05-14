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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.mipl.schoolerp.Fragments.MessageCenter;
import com.mipl.schoolerp.Fragments.ViewDetailInboxView;
import com.mipl.schoolerp.Model.InboxViewModel;
import com.mipl.schoolerp.R;

import java.util.ArrayList;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {

    Context context;
    ArrayList<InboxViewModel> viewModelArrayList;

    public InboxAdapter(Context context, ArrayList<InboxViewModel> viewModelArrayList) {
        this.context = context;
        this.viewModelArrayList = viewModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inbox_tab_layout, viewGroup, false);
        return new InboxAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        InboxViewModel inboxViewModel=viewModelArrayList.get(i);
        myViewHolder.txtSubject.setText(inboxViewModel.getSubject());
        myViewHolder.txtUserName.setText(inboxViewModel.getFrom());
    }

    @Override
    public int getItemCount() {
        return viewModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtUserName,txtSubject;
        ImageView ckImg;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName=itemView.findViewById(R.id.inbox_txtUserName);
            txtSubject=itemView.findViewById(R.id.inbox_txtview);
            ckImg=itemView.findViewById(R.id.inbox_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i=getAdapterPosition();

                    Bundle bundle=new Bundle();
                    bundle.putString("From",viewModelArrayList.get(i).getFrom());
                    bundle.putString("Subject",viewModelArrayList.get(i).getSubject());
                    bundle.putString("Body",viewModelArrayList.get(i).getMsgbody());
                    bundle.putString("Date",viewModelArrayList.get(i).getDate());

                    bundle.putString("attachment1",viewModelArrayList.get(i).getAttachment1());
                    bundle.putString("attachment2",viewModelArrayList.get(i).getAttachment2());
                    bundle.putString("attachment3",viewModelArrayList.get(i).getAttachment3());

                    bundle.putString("File1",viewModelArrayList.get(i).getFile1());
                    bundle.putString("File2",viewModelArrayList.get(i).getFile2());
                    bundle.putString("File3",viewModelArrayList.get(i).getFile3());

                    bundle.putString("msgId",viewModelArrayList.get(i).getId());


                    Fragment fragment = new ViewDetailInboxView();
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
