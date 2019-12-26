package com.ygaps.travelapp.model.login;

public class PasswordRecoveryRequest {
   String type, value;
   public PasswordRecoveryRequest(String type, String value) {
      this.type = type;
      this.value = value;
   }
}
