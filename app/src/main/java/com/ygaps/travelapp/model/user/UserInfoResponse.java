package com.ygaps.travelapp.model.user;

import com.google.gson.annotations.SerializedName;

public class UserInfoResponse {
   @SerializedName("id")
   int id;

   @SerializedName("username")
   String username;

   @SerializedName("fullName")
   String full_name;

   @SerializedName("email")
   String email;

   @SerializedName("phone")
   String phone;

   @SerializedName("address")
   String address;

   @SerializedName("dob")
   String dob;

   @SerializedName("gender")
   int gender;

   @SerializedName("email_verified")
   Boolean email_verified;

   @SerializedName("phone_verified")
   Boolean phone_verified;

   @SerializedName("avatar")
   String avatar;

   public UserInfoResponse(int id, String username, String full_name, String email, String phone, String address, String dob, int gender, Boolean email_verified, Boolean phone_verified, String avatar) {
      this.id = id;
      this.username = username;
      this.full_name = full_name;
      this.email = email;
      this.phone = phone;
      this.address = address;
      this.dob = dob;
      this.gender = gender;
      this.email_verified = email_verified;
      this.phone_verified = phone_verified;
      this.avatar = avatar;
   }

   public UserInfoResponse(UserInfoResponse UserInfoResponse) {
      this.id = UserInfoResponse.getId();
      this.username = UserInfoResponse.getUsername();
      this.full_name = UserInfoResponse.getFull_name();
      this.email = UserInfoResponse.getEmail();
      this.phone = UserInfoResponse.getPhone();
      this.address = UserInfoResponse.getAddress();
      this.dob = UserInfoResponse.getDob();
      this.gender = UserInfoResponse.getGender();
      this.email_verified = UserInfoResponse.getEmail_verified();
      this.phone_verified = UserInfoResponse.getPhone_verified();
      this.avatar = UserInfoResponse.getAvatar();
   }
   public int getId() {
      return id;
   }
   public void setId(int id) {
      this.id = id;
   }
   public String getUsername() {
      return username;
   }
   public void setUsername(String username) {
      this.username = username;
   }
   public String getFull_name() {
      return full_name;
   }
   public void setFull_name(String full_name) {
      this.full_name = full_name;
   }
   public String getEmail() {
      return email;
   }
   public void setEmail(String email) {
      this.email = email;
   }
   public String getPhone() {
      return phone;
   }
   public void setPhone(String phone) {
      this.phone = phone;
   }
   public String getAddress() {
      return address;
   }
   public void setAddress(String address) {
      this.address = address;
   }
   public String getDob() {
      return dob;
   }
   public void setDob(String dob) {
      this.dob = dob;
   }
   public int getGender() {
      return gender;
   }
   public void setGender(int gender) {
      this.gender = gender;
   }
   public Boolean getEmail_verified() {
      return email_verified;
   }
   public void setEmail_verified(Boolean email_verified) {
      this.email_verified = email_verified;
   }
   public Boolean getPhone_verified() {
      return phone_verified;
   }
   public void setPhone_verified(Boolean phone_verified) {
      this.phone_verified = phone_verified;
   }
   public String getAvatar() {
      return avatar;
   }
   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }
}
