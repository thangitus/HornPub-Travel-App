package com.ygaps.travelapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.model.login.LoginRequest;
import com.ygaps.travelapp.model.login.LoginResponse;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;
import com.ygaps.travelapp.service.RegisterFirebase;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
   private static final String TAG = "LoginActivity";
   TextView tvSignUp, tvForgotPassWord;
   Intent intentToSignUp, intentToForgotPassWord, intentToHome;
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
      mapping();
      apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      mPrefs = getSharedPreferences("LoginResponse", MODE_PRIVATE);
      intentToSignUp = new Intent(this, SignUpActivity.class);
      intentToForgotPassWord = new Intent(this, ForgotPassWordActivity.class);
      intentToHome = new Intent(this, HomeActivity.class);
      callbackManager = CallbackManager.Factory.create();
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
            String accessToken = loginResult.getAccessToken().getToken();
            LoginRequest loginRequest = new LoginRequest(accessToken);
            Call<LoginResponse> call = apiService.loginFacebook(loginRequest);
            call.enqueue(new Callback<LoginResponse>() {
               @Override
               public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                  if (response.code() == 200) {
                     Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                     loginResponse = new LoginResponse(response.body());
                     saveLoginResponse(loginResponse);
                     mApplication mApp;
                     mApp = (mApplication) getApplicationContext();
                     mApp.setToken(loginResponse.getToken());
                     startActivity(intentToHome);
                  }
               }
               @Override
               public void onFailure(Call<LoginResponse> call, Throwable t) {

               }
            });
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
      Call<LoginResponse> call = apiService.login(loginRequest);
      call.enqueue(new Callback<LoginResponse>() {
         @Override
         public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
            //If Success(200 - OK)
            if (response.code() == 200) {
               Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
               loginResponse = new LoginResponse(response.body());
               saveLoginResponse(loginResponse);
               mApplication mApp;
               mApp = (mApplication) getApplicationContext();
               mApp.setToken(loginResponse.getToken());
               sendRegistrationToServer();
               startActivity(intentToHome);
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
      editor.putString("token", loginResponse.getToken());
      editor.putString("userId", loginResponse.getUserId());
      editor.apply();
   }

   private void mapping() {
      tvForgotPassWord = findViewById(R.id.textViewForgotPass);
      tvSignUp = findViewById(R.id.textViewSignUp);
      editTextUserName = findViewById(R.id.editTextUserName);
      editTextPass = findViewById(R.id.editTextPass);
      buttonSignIn = findViewById(R.id.buttonSignIn);
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

   @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      callbackManager.onActivityResult(requestCode, resultCode, data);
      super.onActivityResult(requestCode, resultCode, data);
   }

   private void sendRegistrationToServer() {
      mApplication mApp;
      mApp = (mApplication) getApplicationContext();
      String authorization = mApp.getToken();
      SharedPreferences mPrefs = getSharedPreferences("Firebase", MODE_PRIVATE);
      String token = mPrefs.getString("NewToken", null);

      @SuppressLint("HardwareIds")
      String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

      APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      RegisterFirebase registerFirebase = new RegisterFirebase(token, deviceId, 1, "1.0");

      Call<ResponseBody> call = apiService.registerFirebase(authorization, registerFirebase);
      call.enqueue(new Callback<ResponseBody>() {
         @Override
         public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            Log.d(TAG, response.message());
         }
         @Override
         public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.d(TAG, t.toString());

         }
      });
   }
}
