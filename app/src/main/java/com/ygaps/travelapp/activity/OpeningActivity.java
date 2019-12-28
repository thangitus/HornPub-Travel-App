package com.ygaps.travelapp.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;
import com.ygaps.travelapp.service.RegisterFirebase;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpeningActivity extends AppCompatActivity {
   private static final String TAG = "OpeningActivity";
   SharedPreferences mPrefs;
   Intent intent;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_opening);
      mPrefs = getSharedPreferences("LoginResponse", MODE_PRIVATE);
      String token = mPrefs.getString("token", null);
      if (token == null)
         intent = new Intent(this, LoginActivity.class);
      else {
         mApplication mApp;
         mApp = (mApplication) getApplicationContext();
         mApp.setToken(token);
         mApp.setUserId(mPrefs.getString("userId", null));
         intent = new Intent(this, HomeActivity.class);
      }
      new Thread(new Runnable() {
         @Override
         public void run() {
            try {
               Thread.sleep(3500);
               startActivity(intent);
               finish();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }).start();
   }
}
