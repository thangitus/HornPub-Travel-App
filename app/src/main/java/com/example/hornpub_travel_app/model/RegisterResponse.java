package com.example.hornpub_travel_app.model;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
   @SerializedName("id")
   int id;

   @SerializedName("username")
   String username;

   @SerializedName("full_name")
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
   Boolean gender;

   @SerializedName("email_verified")
   Boolean email_verified;

   @SerializedName("phone_verified")
   Boolean phone_verified;

   @SerializedName("message")
   String message;

   public String getMessage() {
      return message;
   }

   public void setMessage(String message) {
      this.message = message;
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

   public Boolean getGender() {
      return gender;
   }

   public void setGender(Boolean gender) {
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

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }
}
