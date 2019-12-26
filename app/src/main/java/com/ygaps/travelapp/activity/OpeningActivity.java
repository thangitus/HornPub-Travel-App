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
      FirebaseInstanceId.getInstance().getInstanceId()
              .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                 @Override
                 public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()) {
                       Log.w(TAG, "getInstanceId failed", task.getException());
                       return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();
                    mApplication mApp;
                    mApp = (mApplication) getApplicationContext();
                    String authorization = mApp.getToken();
                    @SuppressLint("HardwareIds")
                    String deviceId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

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
                       }
                    });
                 }
              });

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
