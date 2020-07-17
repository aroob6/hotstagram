package com.example.hotstagram.ui.user.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.hotstagram.ui.user.activity.AdvertisementActivity;
import com.example.hotstagram.ui.user.activity.InformationActivity;
import com.example.hotstagram.LoginActivity;
import com.example.hotstagram.MainActivity;
import com.example.hotstagram.ui.user.activity.NotificationActivity;
import com.example.hotstagram.ui.user.activity.ProfileModifyActivity;
import com.example.hotstagram.R;
import com.facebook.login.LoginManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static com.firebase.ui.auth.AuthUI.getInstance;

public class UserFragment extends Fragment {

    Bitmap bitmap;
    private FirebaseAuth mauth; // 파이어베이스 인증 객체
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    View include;
    Button userButton;
    private Context context;
    Intent intent;
    private static final int GALLERY_CODE = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = (View)inflater.inflate(R.layout.fragment_user, container, false);

        include = root.findViewById(R.id.include);

        context = container.getContext();
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction().replace(R.id.tab_fragment_user, new UserTablayoutFragment()).commit();

        //Firebase 로그인한 사용자 정보
        mauth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = mauth.getCurrentUser();

        navigationView = root.findViewById(R.id.navigation_setting);
        View header = navigationView.getHeaderView(0);

        ImageView user_Profile = root.findViewById(R.id.user_profile);
        ImageView nav_user_photo = header.findViewById(R.id.nav_user_photo);

        Thread mThread= new Thread(){
            @Override
            public void run() {
                try{
                    //현재로그인한 사용자 정보를 통해 PhotoUrl 가져오기
                    URL url = new URL(firebaseUser.getPhotoUrl().toString());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (MalformedURLException ee) {
                    ee.printStackTrace();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        mThread.start();

        try{
            mThread.join();
            //변환한 bitmap적용
            user_Profile.setImageBitmap(bitmap);
            nav_user_photo.setImageBitmap(bitmap);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        TextView nav_user_name = header.findViewById(R.id.nav_user_name);
        TextView toolbar_user_name = root.findViewById(R.id.toolbar_user_name);
        TextView user_name = root.findViewById(R.id.tv_user_name);
        nav_user_name.setText(firebaseUser.getDisplayName());
        toolbar_user_name.setText(firebaseUser.getDisplayName());
        user_name.setText(firebaseUser.getDisplayName());

        // 툴바
        toolbar = include.findViewById(R.id.toolbar);
        MainActivity activity = (MainActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");

        // 네비게이션 드로어
        drawerLayout = root.findViewById(R.id.drawer_setting);
        navigationView = root.findViewById(R.id.navigation_setting);

        Log.e("drawerLayout",drawerLayout.getId()+" ");
        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
              //  intent = getActivity().getIntent();
                switch (menuItem.getItemId())
                {
                    case R.id.notification:
                        Toast.makeText(context, "공지창입니다.", Toast.LENGTH_SHORT).show();
                        intent= new Intent(getApplicationContext(), NotificationActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.information:
                        Toast.makeText(context, "정보창입니다.", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(), InformationActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.advertising:
                        Toast.makeText(context, "광고창입니다.", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(), AdvertisementActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.logout:
                        // FirebaseAuth.getInstance().signOut();
                        mauth.signOut();
                        LoginManager.getInstance().logOut();
                        Toast.makeText(context, "로그아웃.", Toast.LENGTH_SHORT).show();
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        break;

                    default: return true;
                }

                DrawerLayout drawer = getActivity().findViewById(R.id.drawer_setting);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // 프로필 수정 버튼
        userButton = include.findViewById(R.id.user_button);
        userButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), ProfileModifyActivity.class);
                startActivity(intent);
            }
        });

        // 프로필 사진 클릭 시 동작
        ImageView user_profile = include.findViewById(R.id.user_profile);
        ImageView user_profile_add = include.findViewById(R.id.user_profile_add);
        user_profile_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }
        });
        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }
        });

        return root;
    }

   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //onActivityResult에서는 callbackManager에 로그인 결과를 넘겨줌
        if (requestCode == GALLERY_CODE) {
            System.out.println(data.getData());
            System.out.println(getPath(data.getData()));
        }
    }

    public String getPath(Uri uri){

        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(getActivity(),uri,proj,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);
    }*/
}