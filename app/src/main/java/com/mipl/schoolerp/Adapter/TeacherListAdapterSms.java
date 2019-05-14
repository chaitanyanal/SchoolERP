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
import com.mipl.schoolerp.Model.TeacherModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import java.util.ArrayList;


public class TeacherListAdapterSms extends RecyclerView.Adapter<TeacherListAdapterSms.MyViewHolder> implements TeacherDetailSmsInterface {

    Context context;
    ArrayList<TeacherModel> teacherModelArrayListSms;
    ArrayList<String> namesSms;

    ArrayList<String> AllStringsSms=new ArrayList<>();


    public TeacherListAdapterSms(Context context, ArrayList<TeacherModel> teacherModelArrayList) {
        this.context = context;
        this.teacherModelArrayListSms = teacherModelArrayList;
    }

    @NonNull
    @Override
    public TeacherListAdapterSms.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_list, parent, false);
        return new TeacherListAdapterSms.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherListAdapterSms.MyViewHolder myViewHolder, final int i) {

        final int position=i;

        final TeacherModel teacherModel=teacherModelArrayListSms.get(i);
        myViewHolder.textView.setText(teacherModel.getTeacherName());
        myViewHolder.checkBox.setChecked(teacherModel.getChecked());

        myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                try{
                    if (isChecked){

                        SendDetailsModule sendDetailsModule=new SendDetailsModule();
                        sendDetailsModule.setChecked(true);
                        sendDetailsModule.setName(teacherModelArrayListSms.get(position).getTeacherName());
                        sendDetailsModule.setId(teacherModelArrayListSms.get(position).getTeacherId());
                        sendDetailsModule.setRole(teacherModelArrayListSms.get(position).getTeacherRole());

                        String Tid=teacherModelArrayListSms.get(position).getTeacherId();

                        for (int i = 0; i< Url.ArrayListOfDetailsSms.size(); i++){
                            SendDetailsModule DetailsModule=Url.ArrayListOfDetailsSms.get(i);
                            if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                Url.ArrayListOfDetailsSms.remove(i);
                            }
                        }

                        Url.ArrayListOfDetailsSms.add(sendDetailsModule);

                        // AllStrings.add(teacherModelArrayList.get(position).getTeacherName());

                        onItemClick(Url.ArrayListOfDetailsSms);

                    }else {

                        SendDetailsModule sendDetailsModule=new SendDetailsModule();
                        sendDetailsModule.setChecked(true);
                        sendDetailsModule.setName(teacherModel.getTeacherName());
                        sendDetailsModule.setId(teacherModelArrayListSms.get(position).getTeacherId());
                        sendDetailsModule.setRole(teacherModelArrayListSms.get(position).getTeacherRole());

                        String Tid=teacherModelArrayListSms.get(position).getTeacherId();

                        for (int i=0;i<Url.ArrayListOfDetailsSms.size();i++){
                            SendDetailsModule DetailsModule=Url.ArrayListOfDetailsSms.get(i);
                            if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                Url.ArrayListOfDetailsSms.remove(i);
                            }
                        }

                        for (int i=0;i<AllStringsSms.size();i++) {
                            if (AllStringsSms.get(i).equalsIgnoreCase(teacherModelArrayListSms.get(position).getTeacherName())){

                                AllStringsSms.remove(i);
                            }
                        }

                        onItemClick(Url.ArrayListOfDetailsSms);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return teacherModelArrayListSms.size();
    }


    @Override
    public void onItemClick(ArrayList<SendDetailsModule> interfaceList) {

        SmsSendTo smsSendTo=new SmsSendTo();
        smsSendTo.SetDataEdittext(interfaceList);

        /*for (int i=0;i<interfaceList.size();i++){
            //  Log.d("asas",name.get(i));
        }*/

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.teacherName);
            checkBox=itemView.findViewById(R.id.teacherId);

            namesSms=new ArrayList<>();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        final int pos = getAdapterPosition();

                        final TeacherModel teacherModel=teacherModelArrayListSms.get(pos);

                        if (teacherModelArrayListSms.get(pos).getChecked()){
                            checkBox.setChecked(false);
                            teacherModelArrayListSms.get(pos).setChecked(false);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setName(teacherModel.getTeacherName());
                            sendDetailsModule.setId(teacherModelArrayListSms.get(pos).getTeacherId());
                            sendDetailsModule.setRole(teacherModelArrayListSms.get(pos).getTeacherRole());

                            for (int i=0;i<Url.ArrayListOfDetailsSms.size();i++){
                                SendDetailsModule DetailsModule=Url.ArrayListOfDetailsSms.get(i);
                                if (DetailsModule.getId().equalsIgnoreCase(teacherModelArrayListSms.get(pos).getTeacherId())){
                                    Url.ArrayListOfDetailsSms.remove(i);
                                }
                            }

                            onItemClick(Url.ArrayListOfDetailsSms);

                        }else {
                            checkBox.setChecked(true);
                            teacherModelArrayListSms.get(pos).setChecked(true);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setChecked(true);
                            sendDetailsModule.setName(teacherModel.getTeacherName());
                            sendDetailsModule.setId(teacherModelArrayListSms.get(pos).getTeacherId());
                            sendDetailsModule.setRole(teacherModelArrayListSms.get(pos).getTeacherRole());

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
