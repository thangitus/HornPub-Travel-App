package com.ygaps.travelapp.service;

import com.google.gson.annotations.SerializedName;


public class RegisterFirebase {

   @SerializedName("fcmToken")
   String fcmToken;

   @SerializedName("deviceId")
   String deviceId;

   @SerializedName("platform")
   int platform;

   @SerializedName("appVersion")
   String appVersion;
   public RegisterFirebase(String fcmToken, String deviceId, int platform, String appVersion) {
      this.fcmToken = fcmToken;
      this.deviceId = deviceId;
      this.platform = platform;
      this.appVersion = appVersion;
   }
}
