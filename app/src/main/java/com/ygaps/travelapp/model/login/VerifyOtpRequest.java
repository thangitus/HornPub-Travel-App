package com.ygaps.travelapp.model.login;

public class VerifyOtpRequest {
   String userId, newPassword, verifyCode;
   public VerifyOtpRequest(String userId, String newPassword, String verifyCode) {
      this.userId = userId;
      this.newPassword = newPassword;
      this.verifyCode = verifyCode;
   }
}
