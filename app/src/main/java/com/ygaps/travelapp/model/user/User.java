package com.ygaps.travelapp.model.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
   int id;

   @SerializedName("name")
   String name;

   String phone, avatar;
   public User(User user) {
      this.id = user.getId();
      this.name = user.getName();
      this.phone = user.getPhone();
      this.avatar = user.getAvatar();
   }

   public int getId() {
      return id;
   }
   public void setId(int id) {
      this.id = id;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getPhone() {
      return phone;
   }
   public void setPhone(String phone) {
      this.phone = phone;
   }
   public String getAvatar() {
      return avatar;
   }
   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }
}
