package com.example.hotstagram;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hotstagram.ui.home.PostInfo;
import com.example.hotstagram.ui.home.RecycleAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GetPostDataBase {

    FirebaseFirestore firebaseFirestore;
    Context context;

    ArrayList<QueryDocumentSnapshot> sendDocument;
    Query query;
    CollectionReference collectionReference;
    DocumentReference documentReference;

    FirebaseUser firebaseUser;
    PostInfo postInfo;


    public GetPostDataBase(Context context) {
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        query = firebaseFirestore.collection("Testt").orderBy("postNum");
        collectionReference = firebaseFirestore.collection("Testt");

    }

    //추가
    public void getPostDataBase(final RecycleAdapter recycleAdapter) {
        sendDocument = new ArrayList<>();

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //문서에 있는 값 모드 가져오기
                        if (document.exists()) {
                            sendDocument.add(0, document);
                        } else {
                            Log.e("TAG", "Document 데이터 없음");
                        }
                    }
                    int post;
                    for (post = 0; post < sendDocument.size(); post++) {
                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        documentReference = firebaseFirestore.collection("Inter_Test").document(sendDocument.get(post).get("getUid").toString());
                        if (documentReference != null) {
                            final int finalPost = post;
                            documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.get("ProfileURI") != null) {
                                           /* getProuri = document.get("ProfileURI").toString();
                                            getNickname = document.get("NickName").toString();*/
                                        postInfo = new PostInfo();
                                        postInfo.setProfil(Uri.parse(document.get("ProfileURI").toString()));
                                        if(document.get("NickName") != null) {
                                            postInfo.setName(document.get("NickName").toString());
                                        }else {
                                            postInfo.setName(document.get("Name").toString());
                                        }
                                        postInfo.setNum(sendDocument.get(finalPost).get("postNum").toString());
                                        postInfo.setUid(sendDocument.get(finalPost).get("getUid").toString());
                                        postInfo.setLetter(sendDocument.get(finalPost).get("letter").toString());
                                        postInfo.setImg(sendDocument.get(finalPost).get("getImgUri").toString());
                                        postInfo.setCount(sendDocument.get(finalPost).get("likeList").toString().indexOf(firebaseUser.getUid()));
                                        postInfo.setSize(sendDocument.get(finalPost).get("likeList").toString().length() / 30);
                                        if(sendDocument.get(finalPost).get("commantList") != null) {
                                            postInfo.setCommant(sendDocument.get(finalPost).get("commantList").toString());
                                        }

                                        recycleAdapter.updateData(postInfo);

                                    }else{
                                        postInfo = new PostInfo();
                                        if(document.get("NickName") != null) {
                                            postInfo.setName(document.get("NickName").toString());
                                        }else {
                                            postInfo.setName(document.get("Name").toString());
                                        }
                                        postInfo.setNum(sendDocument.get(finalPost).get("postNum").toString());
                                        postInfo.setUid(sendDocument.get(finalPost).get("getUid").toString());
                                        postInfo.setLetter(sendDocument.get(finalPost).get("letter").toString());
                                        postInfo.setImg(sendDocument.get(finalPost).get("getImgUri").toString());
                                        postInfo.setCount(sendDocument.get(finalPost).get("likeList").toString().indexOf(firebaseUser.getUid()));
                                        postInfo.setSize(sendDocument.get(finalPost).get("likeList").toString().length() / 30);
                                        postInfo.setCommant(sendDocument.get(finalPost).get("commantList").toString());

                                        recycleAdapter.updateData(postInfo);
                                    }
                                }

                            });
                        }

                    }
                }
            }
        });
    }

    //삭제
    public void RemovePostDataBase(final int position, final ArrayList<PostInfo> postInfoArrayList) {

        sendDocument = new ArrayList<>();

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //문서에 있는 값 모드 가져오기
                        sendDocument.add(0, document);
                    }
                    //getImg(sendDocument,position);

                    //Uri getimguri = Uri.parse(sendDocument.get(position).get("getImgUri").toString());
                    String getId = sendDocument.get(position).getId();
                    String postNum = sendDocument.get(position).get("postNum").toString();

                    /*Log.e("getID", "23" + getId);
                    Log.e("postInfoArrayList", postInfoArrayList.get(position).getNum() + "");
                    Log.e("postNum", postNum + "");*/

                    if (postInfoArrayList.get(position).getNum().equals(postNum)) {
                        collectionReference.document(getId)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.e("TAG", "DocumentSnapshot successfully deleted!" + postInfoArrayList.get(position).getImg());
                                    }
                                });
                    }
                } else {
                    Log.d("가져오기 실패", "Error getting documents: ", task.getException());
                }
            }
        });

    }

    //게시물 수정 업데이트
    public void UpdataPostDataBase(final int position, final String updatetext) {
        sendDocument = new ArrayList<>();

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //문서에 있는 값 모드 가져오기
                        sendDocument.add(0, document);
                    }
                    String getId = sendDocument.get(position).getId();
                    collectionReference.document(getId)
                            .update("letter", updatetext)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("업데이트 성공", "성공");
                                    ((MainActivity) MainActivity.mainContext).onResume();

                                }
                            });
                }
            }

        });
    }

    //댓글등록
    public void UpdataPostCommantDataBase(final int position, final String updatetext) {
        sendDocument = new ArrayList<>();

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //문서에 있는 값 모드 가져오기
                        sendDocument.add(0, document);
                    }
                    String getId = sendDocument.get(position).getId();
                    collectionReference.document(getId)
                            .update("commantList", FieldValue.arrayUnion(updatetext))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("업데이트 성공", "성공");
                                    ((MainActivity) MainActivity.mainContext).onResume();

                                }
                            });
                }
            }

        });
    }

    //좋아요업데이트
    public void likeclick(final int position, final String user) {
        sendDocument = new ArrayList<>();

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //문서에 있는 값 모드 가져오기
                        sendDocument.add(0, document);
                    }

                    String getId = sendDocument.get(position).getId();

                    collectionReference.document(getId)
                            .update("likeList", FieldValue.arrayUnion(user))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("업데이트 성공", "성공");

                                }
                            });
                }
            }

        });
    }

    public void unlikeclick(final int position, final String user) {
        sendDocument = new ArrayList<>();

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //문서에 있는 값 모드 가져오기
                        sendDocument.add(0, document);
                    }

                    String getId = sendDocument.get(position).getId();

                    collectionReference.document(getId)
                            .update("likeList", FieldValue.arrayRemove(user))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("업데이트 성공", "성공");
                                }
                            });
                }
            }

        });
    }
}
