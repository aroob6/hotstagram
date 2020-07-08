package com.example.hotstagram.ui.search;

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

import com.example.hotstagram.MainActivity;
import com.example.hotstagram.R;
import com.google.android.material.tabs.TabLayout;

public class SearchFragment extends Fragment {
    Toolbar toolbar;
    ActionBar actionBar;
    Fragment fragment_hot;
    FragmentManager fragmentManager;
    ViewGroup rootView;

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
                //탭
                fragment_hot = new Fragment();
                fragmentManager = getChildFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentLayout,fragment_hot).commitAllowingStateLoss();

                TabLayout tablayout =(TabLayout) rootView.findViewById(R.id.tablayout);
                tablayout.addTab(tablayout.newTab().setText("인기"));
                tablayout.addTab(tablayout.newTab().setText("계정"));
                tablayout.addTab(tablayout.newTab().setText("태그"));
                tablayout.addTab(tablayout.newTab().setText("장소"));

                tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        int position = tab.getPosition();

                        Fragment selected = null;
                        if(position==0){
                            selected =  fragment_hot;
                        }

                        fragmentManager.beginTransaction().replace(R.id.container, selected).commit();

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });


            }
        });


        return rootView;
    }
}