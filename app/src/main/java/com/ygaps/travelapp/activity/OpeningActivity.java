package com.ygaps.travelapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.application.mApplication;

public class OpeningActivity extends AppCompatActivity {
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
         intent = new Intent(this, HomeActivity.class);
      }

      new Thread(new Runnable() {
         @Override
         public void run() {
            try {
               Thread.sleep(4000);
               startActivity(intent);
               finish();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      }).start();
   }
}
