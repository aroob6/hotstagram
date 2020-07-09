package com.example.hotstagram.ui.search;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.hotstagram.R;

public class AccountFragment extends Fragment {
    View view;
    public  AccountFragment() {
        // Required empty public constructor
    }


    public static  AccountFragment newInstance() {
        AccountFragment accountfragment = new  AccountFragment();
        return  accountfragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_hot, container, false);

        return view;
    }
}

