package com.mipl.schoolerp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipl.schoolerp.Model.SchoolNoticeModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import java.util.ArrayList;

public class SchoolNoticesAdapter extends RecyclerView.Adapter<SchoolNoticesAdapter.MyViewHolder>{

     Context context;
     ArrayList<SchoolNoticeModel> noticeModelArrayList;


    public SchoolNoticesAdapter(Context context, ArrayList<SchoolNoticeModel> noticeModelArrayList) {
        this.context = context;
        this.noticeModelArrayList = noticeModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.school_notice_list_data, viewGroup, false);
        return new SchoolNoticesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        SchoolNoticeModel schoolNoticeModel=noticeModelArrayList.get(i);

        myViewHolder.date.setText(schoolNoticeModel.getEndDate());
        myViewHolder.detailsname.setText(schoolNoticeModel.getDescription());

        myViewHolder.detailsname.setPaintFlags(myViewHolder.detailsname.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG );

    }

    @Override
    public int getItemCount() {
        return noticeModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date,detailsname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.date);
            detailsname=itemView.findViewById(R.id.detailsname);


            detailsname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos=getAdapterPosition();

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Url.IP+"/"+noticeModelArrayList.get(pos).getNoticeImage()));
                    context.startActivity(browserIntent);
                }
            });
        }
    }
}
