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
import com.mipl.schoolerp.Interfaces.TeacherDetailsOneInterface;
import com.mipl.schoolerp.Model.SendDetailsModule;
import com.mipl.schoolerp.Model.StudentDetailsModule;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import java.util.ArrayList;

public class StudentListAdapterOne extends RecyclerView.Adapter<StudentListAdapterOne.MyViewHolder> implements TeacherDetailsOneInterface {


    Context context;
    ArrayList<StudentDetailsModule> StudModelArrayListCc;


    public StudentListAdapterOne(Context context, ArrayList<StudentDetailsModule> studModelArrayList) {
        this.context = context;
        StudModelArrayListCc = studModelArrayList;
    }

    @NonNull
    @Override
    public StudentListAdapterOne.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stud_list_item, parent, false);
        return new StudentListAdapterOne.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final StudentListAdapterOne.MyViewHolder myViewHolder, final int i) {

        final int position=i;

        StudentDetailsModule  studentDetailsModule=StudModelArrayListCc.get(i);

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
                        sendDetailsModule.setName(StudModelArrayListCc.get(position).getStudentName());
                        sendDetailsModule.setId(StudModelArrayListCc.get(position).getStudId());
                        sendDetailsModule.setRole(StudModelArrayListCc.get(position).getRole());

                        String Tid=StudModelArrayListCc.get(position).getStudId();

                        for (int i = 0; i< Url.ArrayListOfDetailsCc.size(); i++){
                            SendDetailsModule DetailsModule=Url.ArrayListOfDetailsCc.get(i);
                            if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                Url.ArrayListOfDetailsCc.remove(i);
                            }
                        }

                        Url.ArrayListOfDetailsCc.add(sendDetailsModule);

                        onItemClickCc(Url.ArrayListOfDetailsCc);

                    }else {

                        SendDetailsModule sendDetailsModule=new SendDetailsModule();
                        sendDetailsModule.setChecked(true);
                        sendDetailsModule.setName(StudModelArrayListCc.get(position).getStudentName());
                        sendDetailsModule.setId(StudModelArrayListCc.get(position).getStudId());
                        sendDetailsModule.setRole(StudModelArrayListCc.get(position).getRole());

                        String Tid=StudModelArrayListCc.get(position).getStudId();

                        for (int i=0;i<Url.ArrayListOfDetailsCc.size();i++){
                            SendDetailsModule DetailsModule=Url.ArrayListOfDetailsCc.get(i);
                            if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                Url.ArrayListOfDetailsCc.remove(i);
                                onItemClickCc(Url.ArrayListOfDetailsCc);
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
        return StudModelArrayListCc.size();
    }

    @Override
    public void onItemClickCc(ArrayList<SendDetailsModule> interfaceList) {
        ComposeMailCc composeMailcc =new ComposeMailCc();
        composeMailcc.SetDataEdittextCc(interfaceList);
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

                        final StudentDetailsModule module=StudModelArrayListCc.get(pos);

                        if (StudModelArrayListCc.get(pos).getChecked()){

                            checkBox.setChecked(false);
                            StudModelArrayListCc.get(pos).setChecked(false);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setName(module.getStudentName());
                            sendDetailsModule.setId(module.getStudId());
                            sendDetailsModule.setRole(StudModelArrayListCc.get(pos).getRole());

                            for (int i = 0; i< Url.ArrayListOfDetailsCc.size(); i++){
                                SendDetailsModule DetailsModule=Url.ArrayListOfDetailsCc.get(i);
                                if (DetailsModule.getId().equalsIgnoreCase(module.getStudId())){
                                    Url.ArrayListOfDetailsCc.remove(i);
                                    onItemClickCc(Url.ArrayListOfDetailsCc);
                                }
                            }


                        }else {
                            checkBox.setChecked(true);
                            StudModelArrayListCc.get(pos).setChecked(true);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();

                            sendDetailsModule.setName(StudModelArrayListCc.get(pos).getStudentName());
                            sendDetailsModule.setId(StudModelArrayListCc.get(pos).getStudId());
                            sendDetailsModule.setRole(StudModelArrayListCc.get(pos).getRole());
                            sendDetailsModule.setChecked(true);

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
