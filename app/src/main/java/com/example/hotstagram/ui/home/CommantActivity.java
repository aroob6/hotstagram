package com.example.hotstagram.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hotstagram.GetPostDataBase;
import com.example.hotstagram.R;
import com.example.hotstagram.util.GlideApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommantActivity extends AppCompatActivity {
    TextView tvpostletter;
    TextView tvpostname;
    Intent intent;
    String postletter;
    String postname;
    String proimg;
    CircleImageView ivprofile;

    String commant;
    ListView listView;
    int pos;
    String documentUid;

    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    FirebaseStorage storage;
    StorageReference storageRefpr;

    EditText etcommant;
    TextView tvwrite;
    String getCommant;

    ArrayList<String> namelist;
    ArrayList<String> prourllist;
    ArrayList<String> commantlist;

    CommantListViewAdapter commantListViewAdapter;

    DocumentReference documentReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_commant);

        namelist = new ArrayList<>();
        prourllist = new ArrayList<>();
        commantlist = new ArrayList<>();

        tvpostletter = findViewById(R.id.postletter);
        tvpostname = findViewById(R.id.postname);
        ivprofile = findViewById(R.id.iv_commantprofile);
        listView = findViewById(R.id.commant_listView);
        etcommant = findViewById(R.id.et_commant);
        tvwrite = findViewById(R.id.tv_write);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        intent = getIntent();
        postletter = intent.getExtras().getString("postletter");
        postname = intent.getExtras().getString("postname");
        proimg = intent.getExtras().getString("proimg");
        commant = intent.getExtras().getString("commant");
        pos = intent.getExtras().getInt("pos");
        documentUid = intent.getExtras().getString("document");
        commantListViewAdapter = new CommantListViewAdapter(getApplicationContext());

        //댓글 맨위에 등록
        tvpostletter.setText(postletter);
        tvpostname.setText(postname);
        storage = FirebaseStorage.getInstance();
        storageRefpr = storage.getReferenceFromUrl("gs://hotstagram-cd509.appspot.com/" +proimg);
        GlideApp.with(getApplicationContext()).load(storageRefpr).error(R.drawable.basic_fill).into(ivprofile);

        //댓글보기
        documentReference = firebaseFirestore.collection("Testt").document(documentUid);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                final DocumentSnapshot document2 = task.getResult();
                if(document2.get("commantList") != null) {
                    String getcommants = document2.get("commantList").toString();
                    Log.e("document2.ge", "ㄱㄱ" + getcommants.substring(1, getcommants.length() - 1));

                    String[] splitcommant = getcommants.substring(1, getcommants.length() - 1).split(",");
                    for (int i = 0; i < splitcommant.length; i++) {
                        Log.e("document2.ge" + i, "ㄱㄱ" + splitcommant[i]);
                        if (i % 3 == 0) { namelist.add(splitcommant[i]); }
                        if (i % 3 == 1) { prourllist.add(splitcommant[i]); }
                        if (i % 3 == 2) { commantlist.add(splitcommant[i]); }
                    }

                    for (int i = 0; i < namelist.size(); i++) {
                        commantListViewAdapter.addItem(namelist.get(i), prourllist.get(i), commantlist.get(i));
                        listView.setAdapter(commantListViewAdapter);
                    }
                }
            }
        });



        //댓글 등록
        tvwrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("클릭","클릭");
                getCommant = etcommant.getText().toString();

                firebaseFirestore.collection("Inter_Test").document(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Log.e("가져오기", "ㄱㄱ" + firebaseUser.getUid());
                        DocumentSnapshot document = task.getResult();
                        Log.e("documentName", document.getData().toString()+"");
                        Log.e("documentUri", document.get("ProfileURI").toString()+"");
                        Log.e("getCommant", getCommant+"");

                        commantListViewAdapter.addItem(document.get("NickName").toString(), document.get("ProfileURI").toString(), getCommant);

                        String commantAll = document.get("NickName").toString() + "," + document.get("ProfileURI").toString() + "," + getCommant;
                        GetPostDataBase getPostDataBase = new GetPostDataBase(getApplicationContext());
                        getPostDataBase.UpdataPostCommantDataBase(pos,commantAll);
                        listView.setAdapter(commantListViewAdapter);
                    }
                });
            }
        });





    }
}
