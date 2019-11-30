package com.example.hornpub_travel_app.application;

import android.app.Application;

public class mApplication extends Application {
   private String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI3MSIsInBob25lIjoiMDE4NzYyMTQzNCIsImVtYWlsIjoibmhhdHJhbmdAZ21haWwuY29tIiwiZXhwIjoxNTc2NDA2NzIwNzQ2LCJhY2NvdW50IjoidXNlciIsImlhdCI6MTU3MzgxNDcyMH0.at7-CUH0I1nwH6Dlq9II4gsvaJ5WGUSKXzNNtvYjG-U";
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

}
