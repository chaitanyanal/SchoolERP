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

import com.mipl.schoolerp.Fragments.ComposeMailCc;
import com.mipl.schoolerp.Fragments.ComposeMailTo;
import com.mipl.schoolerp.Interfaces.TeacherDetailsOneInterface;
import com.mipl.schoolerp.Model.SendDetailsModule;
import com.mipl.schoolerp.Model.TeacherModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import java.util.ArrayList;

public class TeacherListAdapterOne extends RecyclerView.Adapter<TeacherListAdapterOne.MyViewHolder> implements TeacherDetailsOneInterface {

    Context context;
    ArrayList<TeacherModel> teacherModelArrayListCc;
    ArrayList<String> namesCc;

    ArrayList<String> AllStringsCc=new ArrayList<>();


    public TeacherListAdapterOne(Context context, ArrayList<TeacherModel> teacherModelArrayList) {
        this.context = context;
        this.teacherModelArrayListCc = teacherModelArrayList;
    }

    @NonNull
    @Override
    public TeacherListAdapterOne.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_list, parent, false);
        return new TeacherListAdapterOne.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherListAdapterOne.MyViewHolder myViewHolder, final int i) {

        final int position=i;

        final TeacherModel teacherModel=teacherModelArrayListCc.get(i);
        myViewHolder.textView.setText(teacherModel.getTeacherName());
        myViewHolder.checkBox.setChecked(teacherModel.getChecked());

        myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                try{
                    if (isChecked){

                        SendDetailsModule sendDetailsModule=new SendDetailsModule();
                        sendDetailsModule.setChecked(true);
                        sendDetailsModule.setName(teacherModelArrayListCc.get(position).getTeacherName());
                        sendDetailsModule.setId(teacherModelArrayListCc.get(position).getTeacherId());
                        sendDetailsModule.setRole(teacherModelArrayListCc.get(position).getTeacherRole());

                        String Tid=teacherModelArrayListCc.get(position).getTeacherId();

                        for (int i = 0; i< Url.ArrayListOfDetailsCc.size(); i++){
                            SendDetailsModule DetailsModule=Url.ArrayListOfDetailsCc.get(i);
                            if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                Url.ArrayListOfDetailsCc.remove(i);
                            }
                        }

                        Url.ArrayListOfDetailsCc.add(sendDetailsModule);

                        // AllStrings.add(teacherModelArrayList.get(position).getTeacherName());

                        onItemClickCc(Url.ArrayListOfDetailsCc);

                    }else {

                        SendDetailsModule sendDetailsModule=new SendDetailsModule();
                        sendDetailsModule.setChecked(true);
                        sendDetailsModule.setName(teacherModel.getTeacherName());
                        sendDetailsModule.setId(teacherModelArrayListCc.get(position).getTeacherId());
                        sendDetailsModule.setRole(teacherModelArrayListCc.get(position).getTeacherRole());

                        String Tid=teacherModelArrayListCc.get(position).getTeacherId();

                        for (int i=0;i<Url.ArrayListOfDetailsCc.size();i++){
                            SendDetailsModule DetailsModule=Url.ArrayListOfDetailsCc.get(i);
                            if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                Url.ArrayListOfDetailsCc.remove(i);
                            }
                        }

                        for (int i=0;i<AllStringsCc.size();i++) {
                            if (AllStringsCc.get(i).equalsIgnoreCase(teacherModelArrayListCc.get(position).getTeacherName())){

                                AllStringsCc.remove(i);
                            }
                        }

                        onItemClickCc(Url.ArrayListOfDetailsCc);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return teacherModelArrayListCc.size();
    }

    @Override
    public void onItemClickCc(ArrayList<SendDetailsModule> name) {

        ComposeMailCc composeMailCc =new ComposeMailCc();
        composeMailCc.SetDataEdittextCc(name);

        for (int i=0;i<name.size();i++){
            //  Log.d("asas",name.get(i));
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.teacherName);
            checkBox=itemView.findViewById(R.id.teacherId);

            namesCc=new ArrayList<>();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        final int pos = getAdapterPosition();

                        final TeacherModel teacherModel=teacherModelArrayListCc.get(pos);

                        if (teacherModelArrayListCc.get(pos).getChecked()){
                            checkBox.setChecked(false);
                            teacherModelArrayListCc.get(pos).setChecked(false);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setName(teacherModel.getTeacherName());
                            sendDetailsModule.setId(teacherModelArrayListCc.get(pos).getTeacherId());
                            sendDetailsModule.setRole(teacherModelArrayListCc.get(pos).getTeacherRole());

                            for (int i=0;i<Url.ArrayListOfDetailsCc.size();i++){
                                SendDetailsModule DetailsModule=Url.ArrayListOfDetailsCc.get(i);
                                if (DetailsModule.getId().equalsIgnoreCase(teacherModelArrayListCc.get(pos).getTeacherId())){
                                    Url.ArrayListOfDetailsCc.remove(i);
                                }
                            }

                            onItemClickCc(Url.ArrayListOfDetailsCc);

                        }else {
                            checkBox.setChecked(true);
                            teacherModelArrayListCc.get(pos).setChecked(true);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setChecked(true);
                            sendDetailsModule.setName(teacherModel.getTeacherName());
                            sendDetailsModule.setId(teacherModelArrayListCc.get(pos).getTeacherId());
                            sendDetailsModule.setRole(teacherModelArrayListCc.get(pos).getTeacherRole());

                            onItemClickCc(Url.ArrayListOfDetailsCc);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
