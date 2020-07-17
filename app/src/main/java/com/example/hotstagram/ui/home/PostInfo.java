package com.example.hotstagram.ui.home;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class PostInfo {
    public String name;
    public Uri img;
    /* public ArrayList<String>imglist ;*/

    public PostInfo(String name, Uri img /*, ArrayList<String> imglist*/){
        this.name = name;
        this.img = img;
        /*this.imglist = imglist;*/
    }

    /*public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }*/

   /* public void setImglist(ArrayList<String> imglist) {
        this.imglist = imglist;
    }

    public ArrayList<String> getImglist() {
        return imglist;
    }*/
}
