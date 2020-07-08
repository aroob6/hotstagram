package com.example.hotstagram;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPicture;
        TextView tvletter;

        MyViewHolder(View view){
            super(view);
            ivPicture = view.findViewById(R.id.iv_picture);
            tvletter = view.findViewById(R.id.tv_letter);
        }
    }

    private ArrayList<PostInfo> postInfoArrayList;
    public RecycleAdapter(ArrayList<PostInfo> postInfoArrayList){
        this.postInfoArrayList = postInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_recyclerview, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.ivPicture.setImageResource(postInfoArrayList.get(position).picture);
        myViewHolder.tvletter.setText(postInfoArrayList.get(position).letter);
    }

    @Override
    public int getItemCount() {
        return postInfoArrayList.size();
    }
}
