package com.example.hornpub_travel_app.model.user;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
   @SerializedName("email")
   String email;

   @SerializedName("password")
   String password;

   @SerializedName("phone")
   String phone;

   @SerializedName("fullName")
   String fullName;

   @SerializedName("address")
   String address;

   @SerializedName("dob")
   String dob;

   @SerializedName("gender")
   Boolean gender;

   public RegisterRequest(String email, String password, String phone, String fullName, String address, String dob, Boolean gender) {
      this.email = email;
      this.password = password;
      this.phone = phone;
      this.fullName = fullName;
      this.address = address;
      this.dob = dob;
      this.gender = gender;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getPhone() {
      return phone;
   }

   public void setPhone(String phone) {
      this.phone = phone;
   }

   public String getFullName() {
      return fullName;
   }

   public void setFullName(String fullName) {
      this.fullName = fullName;
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
}
