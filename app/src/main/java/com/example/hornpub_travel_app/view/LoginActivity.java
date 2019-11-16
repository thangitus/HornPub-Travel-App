package com.example.hornpub_travel_app.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.model.LoginRequest;
import com.example.hornpub_travel_app.model.LoginResponse;
import com.example.hornpub_travel_app.network.APIService;
import com.example.hornpub_travel_app.network.NetworkProvider;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
   TextView tvSignUp, tvForgotPassWord;
   Intent intentToSignUp, intentToForgotPassWord, intentToListTour;
   APIService apiService;
   LoginRequest loginRequest;
   LoginResponse loginResponse;
   EditText editTextUserName, editTextPass;
   Button buttonSignIn;
   SharedPreferences mPrefs;
   CallbackManager callbackManager;
   LoginButton loginButton;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);
      intentToSignUp = new Intent(this, SignUpActivity.class);
      intentToForgotPassWord = new Intent(this, ForgotPassWordActivity.class);
      apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      mapping();
      callbackManager = CallbackManager.Factory.create();


      mPrefs = getSharedPreferences("LoginResponse", MODE_PRIVATE);
      loginResponse = loadLoginResponse();
      intentToListTour = new Intent(this, HomeActivity.class);
      if (loginResponse != null) {
         intentToListTour.putExtra("token", loginResponse.getToken());
         startActivity(intentToListTour);
         finish();
      }
      tvSignUp.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(intentToSignUp);
         }
      });
      tvForgotPassWord.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(intentToForgotPassWord);
         }
      });

      buttonSignIn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if (checkData())
               sendLoginRequest(loginRequest);
         }
      });
      loginButton.setReadPermissions("email");
      setLoginButton();
   }

   private void setLoginButton() {
      loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
         @Override
         public void onSuccess(LoginResult loginResult) {
            Log.d("fb", "success");

            String accessToken = loginResult.getAccessToken().getToken();
         }

         @Override
         public void onCancel() {

         }

         @Override
         public void onError(FacebookException error) {
            Log.d("fb", "err");
         }
      });
   }

   private void sendLoginRequest(LoginRequest loginRequest) {
      apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      Call<LoginResponse> call = apiService.login(loginRequest);
      call.enqueue(new Callback<LoginResponse>() {
         @Override
         public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
            //If Success(200 - OK)
            Toast.makeText(LoginActivity.this, response.code() + "", Toast.LENGTH_SHORT).show();
            if (response.code() == 200) {
               loginResponse = new LoginResponse(response.body());
               saveLoginResponse(loginResponse);
               intentToListTour.putExtra("token", loginResponse.getToken());
               startActivity(intentToListTour);
            }
            if (response.code() == 400) {
               Toast.makeText(LoginActivity.this, "Email/Phone hoặc mật khẩu sai", Toast.LENGTH_SHORT).show();
            }
         }

         @Override
         public void onFailure(Call<LoginResponse> call, Throwable t) {

         }
      });
   }

   private void saveLoginResponse(LoginResponse loginResponse) {
      SharedPreferences.Editor editor = mPrefs.edit();
      Gson gson = new Gson();
      String json = gson.toJson(loginResponse);
      editor.putString("LoginResponse", json);
      editor.apply();
   }

   private LoginResponse loadLoginResponse() {
      Gson gson = new Gson();
      String json = mPrefs.getString("LoginResponse", null);
      if (json != null) {
         LoginResponse loginResponse;
         loginResponse = gson.fromJson(json, LoginResponse.class);
         return loginResponse;
      } else
         return null;
   }

   private void mapping() {
      tvForgotPassWord = findViewById(R.id.textViewForgotPass);
      tvSignUp = findViewById(R.id.textViewSignUp);
      editTextUserName = findViewById(R.id.editTextUserName);
      editTextPass = findViewById(R.id.editTextPass);
      buttonSignIn = findViewById(R.id.buttonSignIn);
//      buttonFaceBook = findViewById(R.id.buttonFaceBook);
//      buttonGoogle = findViewById(R.id.buttonGoogle);
      loginButton = findViewById(R.id.login_button);
   }

   private boolean checkData() {
      String userName, pass;
      userName = editTextUserName.getText().toString();
      pass = editTextPass.getText().toString();
      if (userName.length() < 1) {
         editTextUserName.setError("UserName Không được rỗng");
         return false;
      }
      if (pass.length() < 5) {
         editTextPass.setError("Password không được ít hơn 5 ký tự hay rỗng");
         return false;
      }
      loginRequest = new LoginRequest(userName, pass);
      return true;
   }

   private void sendLoginFbRequest(String accessToken) {

   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      callbackManager.onActivityResult(requestCode, resultCode, data);
      super.onActivityResult(requestCode, resultCode, data);
   }
}
