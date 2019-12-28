package com.ygaps.travelapp.model.notification;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationResponse {
   @SerializedName("tours")
   List<Notification> notificationList;

   public NotificationResponse(NotificationResponse notificationResponse) {
      this.notificationList = notificationResponse.getNotificationList();
   }
   public List<Notification> getNotificationList() {
      return notificationList;
   }
   public void setNotificationList(List<Notification> notificationList) {
      this.notificationList = notificationList;
   }
}
