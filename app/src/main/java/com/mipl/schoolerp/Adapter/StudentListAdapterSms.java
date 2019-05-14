package com.mipl.schoolerp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.mipl.schoolerp.Fragments.SmsSendTo;
import com.mipl.schoolerp.Interfaces.TeacherDetailSmsInterface;
import com.mipl.schoolerp.Model.SendDetailsModule;
import com.mipl.schoolerp.Model.StudentDetailsModule;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import java.util.ArrayList;

public class StudentListAdapterSms extends RecyclerView.Adapter<StudentListAdapterSms.MyViewHolder> implements TeacherDetailSmsInterface {

    Context context;
    ArrayList<StudentDetailsModule> StudModelArrayListSms;


    public StudentListAdapterSms(Context context, ArrayList<StudentDetailsModule> studModelArrayList) {
        this.context = context;
        StudModelArrayListSms = studModelArrayList;
    }

    @NonNull
    @Override
    public StudentListAdapterSms.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stud_list_item, parent, false);
        return new StudentListAdapterSms.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final StudentListAdapterSms.MyViewHolder myViewHolder, final int i) {

        final int position=i;

        StudentDetailsModule  studentDetailsModule=StudModelArrayListSms.get(i);

        myViewHolder.textView.setText(studentDetailsModule.getStudentName());
        myViewHolder.checkBox.setChecked(studentDetailsModule.getChecked());

        if (studentDetailsModule.getChecked()==true){
            myViewHolder.checkBox.setChecked(true);
        }else {
            myViewHolder.checkBox.setChecked(false);
        }


        myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                try{

                    if (isChecked){

                        SendDetailsModule sendDetailsModule=new SendDetailsModule();
                        sendDetailsModule.setName(StudModelArrayListSms.get(position).getStudentName());
                        sendDetailsModule.setId(StudModelArrayListSms.get(position).getStudId());
                        sendDetailsModule.setRole(StudModelArrayListSms.get(position).getRole());

                        String Tid=StudModelArrayListSms.get(position).getStudId();

                        for (int i = 0; i< Url.ArrayListOfDetailsSms.size(); i++){
                            SendDetailsModule DetailsModule=Url.ArrayListOfDetailsSms.get(i);
                            if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                Url.ArrayListOfDetailsSms.remove(i);
                            }
                        }

                        Url.ArrayListOfDetailsSms.add(sendDetailsModule);

                        onItemClick(Url.ArrayListOfDetailsSms);

                    }else {

                        SendDetailsModule sendDetailsModule=new SendDetailsModule();
                        sendDetailsModule.setChecked(true);
                        sendDetailsModule.setName(StudModelArrayListSms.get(position).getStudentName());
                        sendDetailsModule.setId(StudModelArrayListSms.get(position).getStudId());
                        sendDetailsModule.setRole(StudModelArrayListSms.get(position).getRole());

                        String Tid=StudModelArrayListSms.get(position).getStudId();

                        for (int i=0;i<Url.ArrayListOfDetailsSms.size();i++){
                            SendDetailsModule DetailsModule=Url.ArrayListOfDetailsSms.get(i);
                            if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                Url.ArrayListOfDetailsSms.remove(i);
                                onItemClick(Url.ArrayListOfDetailsSms);
                            }
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return StudModelArrayListSms.size();
    }


    @Override
    public void onItemClick(ArrayList<SendDetailsModule> interfaceList) {
        SmsSendTo smsSendTo =new SmsSendTo();
        smsSendTo.SetDataEdittext(interfaceList);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CheckBox checkBox;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.StudentName);
            checkBox=itemView.findViewById(R.id.StudentId);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try{

                        final int pos = getAdapterPosition();

                        final StudentDetailsModule module=StudModelArrayListSms.get(pos);

                        if (StudModelArrayListSms.get(pos).getChecked()){

                            checkBox.setChecked(false);
                            StudModelArrayListSms.get(pos).setChecked(false);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setName(module.getStudentName());
                            sendDetailsModule.setId(module.getStudId());
                            sendDetailsModule.setRole(StudModelArrayListSms.get(pos).getRole());

                            for (int i = 0; i< Url.ArrayListOfDetailsSms.size(); i++){
                                SendDetailsModule DetailsModule=Url.ArrayListOfDetailsSms.get(i);
                                if (DetailsModule.getId().equalsIgnoreCase(module.getStudId())){
                                    Url.ArrayListOfDetailsSms.remove(i);
                                    onItemClick(Url.ArrayListOfDetailsSms);
                                }
                            }


                        }else {
                            checkBox.setChecked(true);
                            StudModelArrayListSms.get(pos).setChecked(true);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();

                            sendDetailsModule.setName(StudModelArrayListSms.get(pos).getStudentName());
                            sendDetailsModule.setId(StudModelArrayListSms.get(pos).getStudId());
                            sendDetailsModule.setRole(StudModelArrayListSms.get(pos).getRole());
                            sendDetailsModule.setChecked(true);

                            onItemClick(Url.ArrayListOfDetailsSms);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
