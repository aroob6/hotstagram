package com.example.hotstagram.ui.search.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.hotstagram.MainActivity;
import com.example.hotstagram.R;
import com.example.hotstagram.ui.search.Adapter.GridViewAdapter;

public class SearchFragment extends Fragment {
    Toolbar toolbar;
    ActionBar actionBar;
    ViewGroup rootView;
    GridView gridView;
    GridViewAdapter gridViewAdapter;

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



        //tab
        EditText et_search = rootView.findViewById(R.id.et_search);
        et_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ConstraintLayout constraintLayout = rootView.findViewById(R.id.tab_fragmentLayout);
                constraintLayout.setVisibility(View.VISIBLE);
                gridView = rootView.findViewById(R.id.gridview);
                gridView.setVisibility(View.INVISIBLE);
                getFragmentManager().beginTransaction().replace(R.id.tab_fragmentLayout, new TablayoutFragment() ).commit();
                return false;
            }
        });

        //그리드뷰
        gridView = rootView.findViewById(R.id.gridview);
        gridViewAdapter = new GridViewAdapter();

        gridViewAdapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.eximg));
        gridViewAdapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.eximg));
        gridViewAdapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.eximg));


        gridView.setAdapter(gridViewAdapter);

        return rootView;
    }

}


