package com.example.hotstagram.ui.search;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.example.hotstagram.MainActivity;
import com.example.hotstagram.R;

public class HotFragment extends Fragment {
    View view;
    public  HotFragment() {
        // Required empty public constructor
    }


    public static  HotFragment newInstance() {
        HotFragment hotfragment = new  HotFragment();
        return  hotfragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_hot, container, false);

        return view;
    }
}
