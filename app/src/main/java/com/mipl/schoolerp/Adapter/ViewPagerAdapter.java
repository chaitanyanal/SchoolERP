package com.mipl.schoolerp.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.mipl.schoolerp.Fragments.ComposeFragment;
import com.mipl.schoolerp.Fragments.DeleteFragment;
import com.mipl.schoolerp.Fragments.InboxFragment;
import com.mipl.schoolerp.Fragments.MessageCenter;
import com.mipl.schoolerp.Fragments.SentFragment;
import com.mipl.schoolerp.Fragments.TrashFragment;
import com.mipl.schoolerp.R;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    Context context;

    private String title[] = {"Compose", "Inbox", "Sent","Trash","Delete"};

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
       // return TabFragment.getInstance(position);

        if (position==0){
            return new ComposeFragment();
        }else if (position==1){
            return new InboxFragment();
        }else if (position==2){
            return new SentFragment();
        }else if (position==3){
            return new TrashFragment();
        }else if (position==4){
            return new DeleteFragment();
        }else {
            return new ComposeFragment();
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
