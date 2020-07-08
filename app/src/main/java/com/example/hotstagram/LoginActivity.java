package com.example.hotstagram;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {

    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);

        //로그인되어있으면 바로 HomeActivity
        if(isLogin()){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }

        //페이스북 버튼 변경
        final LoginButton btn_facebook_login = findViewById(R.id.btn_facebook_login);
        final Button btn_facebook = findViewById(R.id.btn_facebook);

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_facebook_login.performClick();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //페이스북 로그인
    public void facebookLoginOnClick(View v) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                Arrays.asList("email"));
        LoginManager.getInstance().registerCallback( mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult result) {
                GraphRequest request;
                request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    //로그인 성공시
                    @Override
                    public void onCompleted(JSONObject user, GraphResponse response) {
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onError(FacebookException error) {
                Log.e("test", "Error: " + error);
                //finish();
            }
            @Override
            public void onCancel() {
                //finish();
            }
        });
    }

    private boolean isLogin(){
        AccessToken token = AccessToken.getCurrentAccessToken();
        return token!= null;
    }
}