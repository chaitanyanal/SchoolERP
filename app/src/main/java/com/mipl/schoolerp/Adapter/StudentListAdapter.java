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

import com.mipl.schoolerp.Fragments.ComposeMailTo;
import com.mipl.schoolerp.Interfaces.TeacherDetailsInterface;
import com.mipl.schoolerp.Model.SendDetailsModule;
import com.mipl.schoolerp.Model.StudentDetailsModule;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;
import java.util.ArrayList;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder> implements TeacherDetailsInterface {

    Context context;
    ArrayList<StudentDetailsModule> StudModelArrayList;


    public StudentListAdapter(Context context, ArrayList<StudentDetailsModule> studModelArrayList) {
        this.context = context;
        StudModelArrayList = studModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stud_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        final int position=i;

        StudentDetailsModule  studentDetailsModule=StudModelArrayList.get(i);

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
                        sendDetailsModule.setName(StudModelArrayList.get(position).getStudentName());
                        sendDetailsModule.setId(StudModelArrayList.get(position).getStudId());
                        sendDetailsModule.setRole(StudModelArrayList.get(position).getRole());

                        String Tid=StudModelArrayList.get(position).getStudId();

                        for (int i = 0; i< Url.ArrayListOfDetails.size(); i++){
                            SendDetailsModule DetailsModule=Url.ArrayListOfDetails.get(i);
                            if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                Url.ArrayListOfDetails.remove(i);
                            }
                        }

                        Url.ArrayListOfDetails.add(sendDetailsModule);

                        onItemClick(Url.ArrayListOfDetails);

                    }else {

                        SendDetailsModule sendDetailsModule=new SendDetailsModule();
                        sendDetailsModule.setChecked(true);
                        sendDetailsModule.setName(StudModelArrayList.get(position).getStudentName());
                        sendDetailsModule.setId(StudModelArrayList.get(position).getStudId());
                        sendDetailsModule.setRole(StudModelArrayList.get(position).getRole());

                        String Tid=StudModelArrayList.get(position).getStudId();

                        for (int i=0;i<Url.ArrayListOfDetails.size();i++){
                            SendDetailsModule DetailsModule=Url.ArrayListOfDetails.get(i);
                            if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                Url.ArrayListOfDetails.remove(i);
                                onItemClick(Url.ArrayListOfDetails);
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
        return StudModelArrayList.size();
    }

    @Override
    public void onItemClick(ArrayList<SendDetailsModule> interfaceList) {
        ComposeMailTo composeMailTo =new ComposeMailTo();
        composeMailTo.SetDataEdittext(interfaceList);
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

                        final StudentDetailsModule module=StudModelArrayList.get(pos);

                        if (StudModelArrayList.get(pos).getChecked()){

                            checkBox.setChecked(false);
                            StudModelArrayList.get(pos).setChecked(false);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setName(module.getStudentName());
                            sendDetailsModule.setId(module.getStudId());
                            sendDetailsModule.setRole(StudModelArrayList.get(pos).getRole());

                            for (int i = 0; i< Url.ArrayListOfDetails.size(); i++){
                                SendDetailsModule DetailsModule=Url.ArrayListOfDetails.get(i);
                                if (DetailsModule.getId().equalsIgnoreCase(module.getStudId())){
                                    Url.ArrayListOfDetails.remove(i);
                                    onItemClick(Url.ArrayListOfDetails);
                                }
                            }


                        }else {
                            checkBox.setChecked(true);
                            StudModelArrayList.get(pos).setChecked(true);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setChecked(true);
                            sendDetailsModule.setName(StudModelArrayList.get(pos).getStudentName());
                            sendDetailsModule.setId(StudModelArrayList.get(pos).getStudId());
                            sendDetailsModule.setRole(StudModelArrayList.get(pos).getRole());

                            onItemClick(Url.ArrayListOfDetails);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
