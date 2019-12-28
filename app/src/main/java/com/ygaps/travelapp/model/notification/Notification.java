package com.ygaps.travelapp.model.notification;

public class Notification {
   String id, hostName, name, createdOn, hostAvatar;

   public Notification(Notification notification) {
      this.id = notification.getId();
      this.hostName = notification.getHostName();
      this.name = notification.getName();
      this.createdOn = notification.getCreatedOn();
      this.hostAvatar = notification.getHostAvatar();
   }
   public String getHostAvatar() {
      return hostAvatar;
   }
   public void setHostAvatar(String hostAvatar) {
      this.hostAvatar = hostAvatar;
   }
   public String getId() {
      return id;
   }
   public void setId(String id) {
      this.id = id;
   }
   public String getHostName() {
      return hostName;
   }
   public void setHostName(String hostName) {
      this.hostName = hostName;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getCreatedOn() {
      return createdOn;
   }
   public void setCreatedOn(String createdOn) {
      this.createdOn = createdOn;
   }
}
