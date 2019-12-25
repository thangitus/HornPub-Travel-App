package com.ygaps.travelapp.model.user;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
   @SerializedName("userId")
   String userId;

   @SerializedName("token")
   String token;

   @SerializedName("emailVerified")
   Boolean emailVerified;

   @SerializedName("phoneVerified")
   Boolean phoneVerified;

   public LoginResponse(String userId, String token, Boolean emailVerified, Boolean phoneVerified) {
      this.userId = userId;
      this.token = token;
      this.emailVerified = emailVerified;
      this.phoneVerified = phoneVerified;
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

   public Boolean getEmailVerified() {
      return emailVerified;
   }

   public void setEmailVerified(Boolean emailVerified) {
      this.emailVerified = emailVerified;
   }

   public Boolean getPhoneVerified() {
      return phoneVerified;
   }

   public void setPhoneVerified(Boolean phoneVerified) {
      this.phoneVerified = phoneVerified;
   }

   public LoginResponse(LoginResponse loginResponse) {
      this.userId = loginResponse.getUserId();
      this.token = loginResponse.getToken();
      this.emailVerified = loginResponse.getEmailVerified();
      this.phoneVerified = loginResponse.getPhoneVerified();
   }
}
