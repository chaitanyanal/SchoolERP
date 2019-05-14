package com.mipl.schoolerp.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mipl.schoolerp.Adapter.SmsCenterAdapter;
import com.mipl.schoolerp.Adapter.ViewPagerAdapter;
import com.mipl.schoolerp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SmsDashBoard extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    public SmsDashBoard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sms_dash_board, container, false);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        SmsCenterAdapter adapter = new SmsCenterAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.getAdapter().notifyDataSetChanged();
        viewPager.setCurrentItem(0);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).isSelected();

        /*if (tabLayout.getTabAt(1).isSelected()){
            linearLayout.setVisibility(View.VISIBLE);
        }else {
            linearLayout.setVisibility(View.GONE);
        }*/


        setupTabIcons();


        return view;
    }

    private void setupTabIcons() {

        tabLayout.getTabAt(0).setIcon(R.drawable.compose_mail);
        tabLayout.getTabAt(1).setIcon(R.drawable.inbox);
        tabLayout.getTabAt(2).setIcon(R.drawable.sent_email);
        tabLayout.getTabAt(3).setIcon(R.drawable.trash1);



        /*tabLayout.getTabAt(0).setIcon(R.drawable.ic_contact_mail_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_email_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_send_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_delete_sweep_black_24dp);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_clear_black_24dp);*/
    }

}
