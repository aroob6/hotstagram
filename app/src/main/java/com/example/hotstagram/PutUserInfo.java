/*
package com.example.hotstagram;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PutUserInfo {

    private FirebaseFirestore firebaseFireStore;
    private AppCompatActivity activity;
    private FirebaseUser firebaseUser;
    long now = System.currentTimeMillis();

    public PutUserInfo(AppCompatActivity activity) {
        this.activity = activity;
        firebaseFireStore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void PutUserInfo(String userEmail, String userName, String userPhotoUri) {
        DocumentReference documentReference = firebaseFireStore.collection("HG_Test").document(firebaseUser.getDisplayName()+"_"+now);
        documentReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot userInfoSnapshot = task.getResult();
                if (userInfoSnapshot.getData() == null) {
                    Map<String, String> mUserInfo = new HashMap<>();
                    mUserInfo.put("disPlayName", userName);
                    mUserInfo.put("disPlayPhotoUri", userPhotoUri);
                    mUserInfo.put("userName", "none");
                    mUserInfo.put("userStatusMsg", "none");
                    mUserInfo.put("userPhoneNumber", "none");
                    mUserInfo.put("userGender", "none");
                    documentReference.set(mUserInfo).addOnCompleteListener(task2 -> {
                        if (task2.isSuccessful()) {
                            Toast.makeText(activity, "회원이 되신 걸 축하드립니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity, "일시적인 네트워크 에러가 발생했습니다. 잠시 후 다시시도해주세요.", Toast.LENGTH_SHORT).show();
                            ((MainActivity)activity).signOut();
                        }
                    });
                }

                activity.finish();
            }
        });
    }


    */
/*
    firebaseFireStore = FirebaseFirestore
    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    Map<String, Object> Mapuser = new HashMap<>();
        Mapuser.put("name", firebaseUser.getDisplayName());
        Mapuser.put("UID", firebaseUser.getUid());
        Mapuser.put("E-mail", firebaseUser.getEmail());
        Mapuser.put("PhotoURL", "gs://hotstagram-cd509.appspot.com/");

    //데이터 추가
        firebaseFirestore.collection("HG_Test").document(firebaseUser.getDisplayName()+"_"+now)
            .set(Mapuser)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            Log.e("성공 : ", "" + FirebaseAuth.getInstance().getCurrentUser());
        }
    });
            .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

        }
    });*//*


}

*/
