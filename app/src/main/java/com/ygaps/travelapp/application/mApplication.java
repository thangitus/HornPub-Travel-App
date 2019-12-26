package com.ygaps.travelapp.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.provider.Settings.Secure;

import com.google.firebase.FirebaseApp;

public class mApplication extends Application {
   private String token;
   public String getUserId() {
      return userId;
   }
   public void setUserId(String userId) {
      this.userId = userId;
   }
   private String userId;
   private String avatarBase64 = "";

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public String getAvatarBase64() {
      return avatarBase64;
   }

   public void setAvatarBase64(String avatarBase64) {
      this.avatarBase64 = avatarBase64;
   }

   @Override
   public void onCreate() {
      super.onCreate();
      FirebaseApp.initializeApp(this);

   }
}
