package com.mipl.schoolerp.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mipl.schoolerp.Adapter.CustomAdapter;
import com.mipl.schoolerp.Adapter.ViewPagerAdapter;
import com.mipl.schoolerp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageCenter extends Fragment {

    Spinner spinner;

    private TabLayout tabLayout;
    private ViewPager viewPager;


    public MessageCenter() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=  inflater.inflate(R.layout.fragment_message_center, container, false);

       // spinner=view.findViewById(R.id.simpleSpinner);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.getAdapter().notifyDataSetChanged();
        viewPager.setCurrentItem(0);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).isSelected();

        setupTabIcons();

        String[] countryNames={"Select","Compose","Inbox","Sent","Trash","Delete"};
        int flags[] = {0,R.drawable.compose_mail, R.drawable.inbox, R.drawable.sent_email, R.drawable.trash1, R.drawable.delete_mail};

        /*CustomAdapter customAdapter=new CustomAdapter(getContext(),flags,countryNames);
        spinner.setAdapter(customAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        return view;
    }


    private void setupTabIcons() {

        tabLayout.getTabAt(0).setIcon(R.drawable.compose_mail);
        tabLayout.getTabAt(1).setIcon(R.drawable.inbox);
        tabLayout.getTabAt(2).setIcon(R.drawable.sent_email);
        tabLayout.getTabAt(3).setIcon(R.drawable.trash1);
        tabLayout.getTabAt(4).setIcon(R.drawable.delete_mail);

        /*tabLayout.getTabAt(0).setIcon(R.drawable.ic_contact_mail_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_email_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_send_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_delete_sweep_black_24dp);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_clear_black_24dp);*/
    }

}
