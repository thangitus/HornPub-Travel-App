package com.ygaps.travelapp.model.login;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
   @SerializedName("emailPhone")
   String emailPhone;

   @SerializedName("password")
   String password;

    @SerializedName("accessToken")
   String accessToken;




   public LoginRequest(String emailPhone, String password) {
      this.emailPhone = emailPhone;
      this.password = password;
   }
   public LoginRequest(String accessToken) {
      this.accessToken = accessToken;
   }
   public String getEmailPhone() {
      return emailPhone;
   }

   public void setEmailPhone(String emailPhone) {
      this.emailPhone = emailPhone;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

}
