package com.example.hornpub_travel_app.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
   @SerializedName("userId")
   String userId;

   @SerializedName("token")
   String token;

   @SerializedName("message")
   String message;

   @SerializedName("avatar")
   String avatar;

   @SerializedName("fullName")
   String fullName;

   public LoginResponse(String userId, String token, String message, String avatar, String fullName) {
      this.userId = userId;
      this.token = token;
      this.message = message;
      this.avatar = avatar;
      this.fullName = fullName;
   }

   public String getUserId() {
      return userId;
   }

   public void setUserId(String userId) {
      this.userId = userId;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
   }

   public String getAvatar() {
      return avatar;
   }

   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }

   public String getFullName() {
      return fullName;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
   }
}
