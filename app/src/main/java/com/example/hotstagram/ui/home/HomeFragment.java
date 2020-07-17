package com.example.hotstagram.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.hotstagram.MainActivity;
import com.example.hotstagram.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    Toolbar toolbar;
    ActionBar actionBar;

    //데이터베이스
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    private FirebaseStorage storage;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    long now = System.currentTimeMillis();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        //데이터베이스
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

      /*  Map<String, Object> Mapuser = new HashMap<>();
        Mapuser.put("name", firebaseUser.getDisplayName());
        Mapuser.put("UID", firebaseUser.getUid());
        Mapuser.put("E-mail", firebaseUser.getEmail());
        Mapuser.put("PhotoURL", "gs://hotstagram-cd509.appspot.com/");
        //StorageReference storageRef = storage.getReferenceFromUrl("gs://hotstagram-cd509.appspot.com/");

        //데이터 추가
        firebaseFirestore.collection("HG_Test").document(firebaseUser.getDisplayName()+"_"+now)
                .set(Mapuser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("성공 : ", "" + FirebaseAuth.getInstance().getCurrentUser());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
*/

        //데이터 가져오기
        firebaseFirestore.collection("HG_Test").document("User_Info")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Log.e("document값!!!", "" + task.getResult());
                }
            }
        });


        //툴바
        View view = (View) inflater.inflate(R.layout.fragment_home, container, false);
        MainActivity activity = (MainActivity) getActivity();
        toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);

        //카메라
        ImageView camera = rootView.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);

            }
        });
        return rootView;

    }
/*
    public void SuccessUrl(String getImgUri) {

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReferenceFromUrl(getImgUri);

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("다운로드 성공", "성공" + uri);
                ArrayList<PostInfo> postInfoArrayList = new ArrayList<>();
                *//*for(int i = 0; i<postInfoArrayList.size(); i++) {
                    //postInfoArrayList.add(new PostInfo(user.getDisplayName(), uri));
                    postInfoArrayList.add(new PostInfo(user.getDisplayName(), uri))
                }*//*

                Log.e("추가 성공1", "" + uri);

                mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);

                RecycleAdapter recycleAdapter = new RecycleAdapter(postInfoArrayList);
                mRecyclerView.setAdapter(recycleAdapter);

                postInfoArrayList.add(new PostInfo(user.getDisplayName(), uri));
                postInfoArrayList.add(new PostInfo(user.getDisplayName(), uri));

                recycleAdapter.notifyDataSetChanged();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("다운로드 실패", "실패");
            }
        });
    }*/

}