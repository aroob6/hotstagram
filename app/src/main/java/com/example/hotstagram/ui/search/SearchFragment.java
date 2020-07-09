package com.example.hotstagram.ui.search;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.hotstagram.MainActivity;
import com.example.hotstagram.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    Toolbar toolbar;
    ActionBar actionBar;
    Fragment fragment_hot;
    FragmentManager fragmentManager = getFragmentManager();
    ViewGroup rootView;

    private TabLayout tabLayout;
    private ArrayList <String> tabNames = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup)inflater.inflate(R.layout.fragment_search, container, false);

        //툴바
        View view = (View) inflater.inflate(R.layout.fragment_home, container, false);
        MainActivity activity = (MainActivity) getActivity();
        toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);



        EditText et_search = rootView.findViewById(R.id.et_search);
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*loadTabName();
                setTabLayout();
                setViewPager();*/
            }
        });

        return rootView;
    }
    /*@TargetApi(Build.VERSION_CODES.N)
    private void setTabLayout(){
        tabLayout = rootView.findViewById(R.id.tab);
        tabNames.stream().forEach(name ->tabLayout.addTab(tabLayout.newTab().setText(name)));
    }

    private void loadTabName(){
        tabNames.add("탭1");
        tabNames.add("탭2");
        tabNames.add("탭3");
    }

    private void setViewPager() {
       TablayoutAdapter adapter = new TablayoutAdapter(fragmentManager);
        ViewPager viewPager = rootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
    }
