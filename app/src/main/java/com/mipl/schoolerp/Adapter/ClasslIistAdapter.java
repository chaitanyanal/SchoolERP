package com.mipl.schoolerp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mipl.schoolerp.R;

import java.util.ArrayList;

public class ClasslIistAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> ClassNames;
    LayoutInflater inflter;

    public ClasslIistAdapter(Context context, ArrayList<String> classNames) {

        this.context = context;
        ClassNames = classNames;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return ClassNames.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = inflter.inflate(R.layout.classlist_layout, null);

        TextView names = (TextView) view.findViewById(R.id.classname);
        names.setTextColor(Color.BLACK);

        RadioButton rd=view.findViewById(R.id.radio);

        names.setText(ClassNames.get(position));

        return view;
    }
}
