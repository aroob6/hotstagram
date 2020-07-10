package com.example.hotstagram.ui.search.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.hotstagram.R;
import com.example.hotstagram.ui.search.GridViewItem;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    private ArrayList<GridViewItem> arrayList = new ArrayList<>();
    public GridViewAdapter(){

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Context context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fragment_search_gridview,viewGroup,false);
        }
        ImageView imageView = (ImageView)view.findViewById(R.id.iv_img);

        GridViewItem gridViewItem = arrayList.get(i);

        imageView.setImageDrawable(gridViewItem.getDrawable());

        return view;
    }
    public void addItem(Drawable drawable){
        GridViewItem gridViewItem = new GridViewItem();
        gridViewItem.setDrawable(drawable);

        arrayList.add(gridViewItem);
    }
}
