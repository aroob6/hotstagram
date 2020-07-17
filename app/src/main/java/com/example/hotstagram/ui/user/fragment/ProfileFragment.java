package com.example.hotstagram.ui.user.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.hotstagram.R;
import com.example.hotstagram.ui.search.adapter.GridViewAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class ProfileFragment extends Fragment {

    FirebaseStorage storage;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private GridViewAdapter gridViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_profile, container, false);

        // GridView 적용
        GridView usergridView = root.findViewById(R.id.user_gridview);
        GridViewAdapter userGridViewAdapter = new GridViewAdapter();
        userGridViewAdapter.addItem(ContextCompat.getDrawable(getActivity(),R.drawable.totoro));
        usergridView.setAdapter(userGridViewAdapter);

        //현재 사용자
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        storage = FirebaseStorage.getInstance("gs://hotstagram-cd509.appspot.com");

        //생성된 FirebaseStorage를 참조하는 storage 생성
        StorageReference storageRef = storage.getReference();

        //Storage 내부의 images 폴더 안의 image.jpg 파일명을 가리키는 참조 생성
        StorageReference pathReference = storageRef.child("images/IMG_20200716_070715.jpg");
        try{
            File dir = new File(Environment.getExternalStorageDirectory() + "/images");
            final File file = new File(dir, "images/IMG_20200716_070715.jpg");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            final FileDownloadTask fileDownloadTask = pathReference.getFile(file);
            fileDownloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                   /* tv.setText(file.getAbsolutePath());
                    iv.setImageURI(Uri.fromFile(file));*/
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.e("zzz", "fail");
                }
            }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {

                }
            });
        } catch(
        Exception e) {
            e.printStackTrace();
        }

        return root;
    }
}
