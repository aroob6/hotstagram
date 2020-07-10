package com.example.hotstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hotstagram.ui.basic.BasicFragment;
import com.example.hotstagram.ui.home.HomeFragment;
import com.example.hotstagram.ui.search.SearchFragment;
import com.example.hotstagram.ui.upload.UploadFragment;
import com.example.hotstagram.ui.user.UserFragment;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    Fragment fragment_home, fragment_search, fragment_upload, fragment_basic, fragment_user;
    private static final int REQUEST_CODE = 0;
    ImageView insertimg;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        fragmentManager = getSupportFragmentManager();
        fragment_home = new HomeFragment();
        fragment_search = new SearchFragment();
        fragment_upload = new UploadFragment();
        fragment_basic = new BasicFragment();
        fragment_user = new UserFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout,fragment_home).commitAllowingStateLoss();

        insertimg = findViewById(R.id.image);

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
                        /*getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLayout, fragment_upload).commitAllowingStateLoss();*/
                        intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, REQUEST_CODE);
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
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    insertimg.setImageBitmap(img);

                } catch (Exception e) {
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
            LoginManager.getInstance().logOut();
            Toast.makeText(this, "로그아웃", Toast.LENGTH_LONG).show();
            intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
    }
}

