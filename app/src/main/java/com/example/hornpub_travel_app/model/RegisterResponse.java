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


   public RegisterResponse(int id, String username, String full_name, String email, String phone, String address, String dob, Boolean gender, Boolean email_verified, Boolean phone_verified){
      this.username = username;
      this.full_name = full_name;
      this.email = email;
      this.phone = phone;
      this.address = address;
      this.dob = dob;
      this.gender = gender;
      this.email_verified = email_verified;
      this.phone_verified = phone_verified;
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


   public RegisterResponse(RegisterResponse registerResponse) {
      this.id = registerResponse.getId();
      this.username = registerResponse.getUsername();
      this.full_name = registerResponse.getFull_name();
      this.email = registerResponse.getEmail();
      this.phone = registerResponse.getPhone();
      this.address = registerResponse.getAddress();
      this.dob = registerResponse.getDob();
      this.gender = registerResponse.getGender();
      this.email_verified = registerResponse.getEmail_verified();
      this.phone_verified = registerResponse.getPhone_verified();
   }
}
