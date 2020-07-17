package com.example.hotstagram.ui.home;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotstagram.R;
import com.example.hotstagram.ui.home.PostInfo;

import java.util.ArrayList;

public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPicture;
        TextView tvname;

        MyViewHolder(View view){
            super(view);
            ivPicture = view.findViewById(R.id.iv_picture);
            tvname = view.findViewById(R.id.tv_name);
        }

    }

    private ArrayList<PostInfo> postInfoArrayList;

    public RecycleAdapter(ArrayList<PostInfo> postInfoArrayList){
        this.postInfoArrayList = postInfoArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_recyclerview, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        Glide.with(holder.itemView)
                .load(postInfoArrayList.get(position).img).into(myViewHolder.ivPicture);

        myViewHolder.tvname.setText(postInfoArrayList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return postInfoArrayList.size();
    }
}
