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
import com.mipl.schoolerp.Model.TeacherModel;
import com.mipl.schoolerp.R;
import com.mipl.schoolerp.Util.Url;

import java.util.ArrayList;


public class TeacherListAdapter extends RecyclerView.Adapter<TeacherListAdapter.MyViewHolder> implements TeacherDetailsInterface {

    Context context;
    ArrayList<TeacherModel> teacherModelArrayList;
    ArrayList<String> names;

    ArrayList<String> AllStrings=new ArrayList<>();


    public TeacherListAdapter(Context context, ArrayList<TeacherModel> teacherModelArrayList) {
        this.context = context;
        this.teacherModelArrayList = teacherModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        final int position=i;

        final TeacherModel teacherModel=teacherModelArrayList.get(i);
        myViewHolder.textView.setText(teacherModel.getTeacherName());
        myViewHolder.checkBox.setChecked(teacherModel.getChecked());

        myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                try{
                    if (isChecked){

                        SendDetailsModule sendDetailsModule=new SendDetailsModule();
                        sendDetailsModule.setChecked(true);
                        sendDetailsModule.setName(teacherModelArrayList.get(position).getTeacherName());
                        sendDetailsModule.setId(teacherModelArrayList.get(position).getTeacherId());
                        sendDetailsModule.setRole(teacherModelArrayList.get(position).getTeacherRole());

                        String Tid=teacherModelArrayList.get(position).getTeacherId();

                        for (int i=0;i<Url.ArrayListOfDetails.size();i++){
                            SendDetailsModule DetailsModule=Url.ArrayListOfDetails.get(i);
                            if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                Url.ArrayListOfDetails.remove(i);
                            }
                        }

                        Url.ArrayListOfDetails.add(sendDetailsModule);

                       // AllStrings.add(teacherModelArrayList.get(position).getTeacherName());

                        onItemClick(Url.ArrayListOfDetails);

                    }else {

                        SendDetailsModule sendDetailsModule=new SendDetailsModule();
                        sendDetailsModule.setChecked(true);
                        sendDetailsModule.setName(teacherModel.getTeacherName());
                        sendDetailsModule.setId(teacherModelArrayList.get(position).getTeacherId());
                        sendDetailsModule.setRole(teacherModelArrayList.get(position).getTeacherRole());

                        String Tid=teacherModelArrayList.get(position).getTeacherId();

                        for (int i=0;i<Url.ArrayListOfDetails.size();i++){
                            SendDetailsModule DetailsModule=Url.ArrayListOfDetails.get(i);
                            if (DetailsModule.getId().equalsIgnoreCase(Tid)){
                                Url.ArrayListOfDetails.remove(i);
                            }
                        }

                        for (int i=0;i<AllStrings.size();i++) {
                            if (AllStrings.get(i).equalsIgnoreCase(teacherModelArrayList.get(position).getTeacherName())){

                                AllStrings.remove(i);
                            }
                        }

                        onItemClick(Url.ArrayListOfDetails);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return teacherModelArrayList.size();
    }

    @Override
    public void onItemClick(ArrayList<SendDetailsModule> name) {

        ComposeMailTo composeMailTo =new ComposeMailTo();
        composeMailTo.SetDataEdittext(name);

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

            names=new ArrayList<>();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        final int pos = getAdapterPosition();

                        final TeacherModel teacherModel=teacherModelArrayList.get(pos);

                        if (teacherModelArrayList.get(pos).getChecked()){
                            checkBox.setChecked(false);
                            teacherModelArrayList.get(pos).setChecked(false);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setName(teacherModel.getTeacherName());
                            sendDetailsModule.setId(teacherModelArrayList.get(pos).getTeacherId());
                            sendDetailsModule.setRole(teacherModelArrayList.get(pos).getTeacherRole());

                            for (int i=0;i<Url.ArrayListOfDetails.size();i++){
                                SendDetailsModule DetailsModule=Url.ArrayListOfDetails.get(i);
                                if (DetailsModule.getId().equalsIgnoreCase(teacherModelArrayList.get(pos).getTeacherId())){
                                    Url.ArrayListOfDetails.remove(i);
                                }
                            }

                            onItemClick(Url.ArrayListOfDetails);

                        }else {
                            checkBox.setChecked(true);
                            teacherModelArrayList.get(pos).setChecked(true);

                            SendDetailsModule sendDetailsModule=new SendDetailsModule();
                            sendDetailsModule.setChecked(true);
                            sendDetailsModule.setName(teacherModel.getTeacherName());
                            sendDetailsModule.setId(teacherModelArrayList.get(pos).getTeacherId());
                            sendDetailsModule.setRole(teacherModelArrayList.get(pos).getTeacherRole());

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
