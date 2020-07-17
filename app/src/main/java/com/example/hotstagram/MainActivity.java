package com.example.hotstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hotstagram.ui.basic.BasicFragment;
import com.example.hotstagram.ui.home.HomeFragment;
import com.example.hotstagram.ui.home.PostInfo;
import com.example.hotstagram.ui.search.fragment.SearchFragment;
import com.example.hotstagram.ui.user.fragment.UserFragment;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    Fragment fragment_home, fragment_search, fragment_upload, fragment_basic, fragment_user;
    private static final int GALLERY_IMG = 10;
    ImageView insertimg;
    Intent intent;

    View view;
    RecyclerView recyclerView;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    Uri phtoUri;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    BottomNavigationView navView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");*/


        navView = findViewById(R.id.nav_view);
        fragmentManager = getSupportFragmentManager();
        fragment_home = new HomeFragment();
        fragment_search = new SearchFragment();
        fragment_basic = new BasicFragment();
        fragment_user = new UserFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout,fragment_home).commitAllowingStateLoss();


        //bottom
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment_home).commitAllowingStateLoss();
                        return true;
                    case R.id.navigation_search_bold:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment_search).commitAllowingStateLoss();
                        return true;
                    case R.id.navigation_upload_01: {
                        Uploadimg();
                        return true;
                    }
                    case R.id.navigation_basic:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment_basic).commitAllowingStateLoss();
                        return true;
                    case R.id.navigation_user:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment_user).commitAllowingStateLoss();
                        return true;

                    default: return false;
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case GALLERY_IMG:{
                if (resultCode == RESULT_OK) {
                    try {
                        view = fragment_home.getView();
                        recyclerView = view.findViewById(R.id.recycler_view);
                        insertimg = recyclerView.findViewById(R.id.iv_picture);

                        phtoUri = data.getData();

                        setFirebaseStorage();

                    } catch (Exception e) {
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
                }
            }break;
        }

    }

    public void Uploadimg(){
        intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.putExtra("upload",100);
        startActivityForResult(intent, GALLERY_IMG);
    }

    public void setFirebaseStorage(){

        firebaseStorage = FirebaseStorage.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //firebaseStorage 이미지 업로드
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMHH_mmss");
        Date now = new Date();
        String filename = formatter.format(now) + ".png";
        storageReference = firebaseStorage.getReferenceFromUrl("gs://hotstagram-cd509.appspot.com/")
                .child("upload/" + firebaseUser.getUid() + "_" + filename);

        storageReference.putFile(phtoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putInt("upload", 100 );
                fragment_home.setArguments(bundle);

                navView.setSelectedItemId(R.id.navigation_home);
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                Toast.makeText(getApplicationContext(), "업로드 실패!", Toast.LENGTH_SHORT).show();
            }
        });

        // firebaseFirestore 아이디랑 이미지 주소 저장
        Map<String,Object> setmapuserimg = new HashMap<>();

        setmapuserimg.put("getUid",firebaseUser.getUid());
        setmapuserimg.put("getImgUri","gs://hotstagram-cd509.appspot.com/upload/"+ firebaseUser.getUid() + "_" + filename);


        firebaseFirestore.collection("Test").document("SetImg")
                .set(setmapuserimg)
                /*.addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Log.e("성공 11: " , "" + FirebaseAuth.getInstance().getCurrentUser());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });*/
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("성공 : " , "" + FirebaseAuth.getInstance().getCurrentUser());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

}
