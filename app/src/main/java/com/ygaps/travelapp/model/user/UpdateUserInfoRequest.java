package com.ygaps.travelapp.model.user;

import java.io.Serializable;
import java.util.Date;

public class UpdateUserInfoRequest implements Serializable {
   String fullName, email, phone;
   int gender;
   Date dob;

   public UpdateUserInfoRequest(String fullName, String email, String phone, int gender, Date dob) {
      this.fullName = fullName;
      this.email = email;
      this.phone = phone;
      this.gender = gender;
      this.dob = dob;
   }
   public String getFullName() {
      return fullName;
   }
   public void setFullName(String fullName) {
      this.fullName = fullName;
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
   public int getGender() {
      return gender;
   }
   public void setGender(int gender) {
      this.gender = gender;
   }
   public Date getDob() {
      return dob;
   }
   public void setDob(Date dob) {
      this.dob = dob;
   }
}
