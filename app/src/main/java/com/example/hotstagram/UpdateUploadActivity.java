package com.example.hotstagram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hotstagram.util.GlideApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class UpdateUploadActivity extends AppCompatActivity {

    ImageView update_select_img;
    Intent intent;
    String getimg;
    int pos;
    EditText update_et_letter;

    FirebaseStorage storage;
    StorageReference storageRef;

    String updatetext;

    FirebaseFirestore firebaseFirestore;
    ArrayList<QueryDocumentSnapshot> sendDocument;

    View include;
    TextView tvfinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_update);


        update_select_img = findViewById(R.id.update_select_img);

        include = findViewById(R.id.upload_update_toolbar);
        tvfinish = include.findViewById(R.id.tv_finish);

        intent = getIntent();
        getimg = intent.getExtras().getString("setimg");
        pos = intent.getExtras().getInt("setpos");

        Log.e("setpos","" + pos);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://hotstagram-cd509.appspot.com/"+getimg);

        GlideApp.with(getApplicationContext()).load(storageRef).into(update_select_img);

        //업데이트
        tvfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseFirestore = FirebaseFirestore.getInstance();
                sendDocument = new ArrayList<>();
                update_et_letter = findViewById(R.id.update_et_letter);
                updatetext = update_et_letter.getText().toString();

                GetPostDataBase getPostDataBase = new GetPostDataBase(getApplicationContext());
                getPostDataBase.UpdataPostDataBase(pos,updatetext);
                finish();
            }
        });


    }

}
