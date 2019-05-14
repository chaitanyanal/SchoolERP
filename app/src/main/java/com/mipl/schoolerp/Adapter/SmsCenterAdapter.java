package com.mipl.schoolerp.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mipl.schoolerp.Fragments.SentSms;
import com.mipl.schoolerp.Fragments.SmsCenter;
import com.mipl.schoolerp.Fragments.SmsReceived;
import com.mipl.schoolerp.Fragments.SmsScheduled;

public class SmsCenterAdapter extends FragmentPagerAdapter {

    Context context;

    private String title[] = {"Compose", "Received", "Sent","Scheduled"};

    public SmsCenterAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        // return TabFragment.getInstance(position);

        if (position==0){
            return new SmsCenter();
        }else if (position==1){
            return new SmsReceived();
        }else if (position==2){
            return new SentSms();
        }else if (position==3){
            return new SmsScheduled();
        }else {
            return new SmsCenter();
        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
