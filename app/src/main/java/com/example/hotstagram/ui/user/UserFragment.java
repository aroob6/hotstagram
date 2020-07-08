package com.example.hotstagram.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hotstagram.MainActivity;
import com.example.hotstagram.R;

public class UserFragment extends Fragment {

    Toolbar toolbar;
    ActionBar actionBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_user, container, false);

        //툴바
        View view = (View) inflater.inflate(R.layout.fragment_home, container, false);
        MainActivity activity = (MainActivity) getActivity();
        toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        return root;
    }
}