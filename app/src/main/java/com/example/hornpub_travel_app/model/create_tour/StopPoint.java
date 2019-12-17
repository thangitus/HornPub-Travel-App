package com.example.hornpub_travel_app.model.create_tour;

import com.google.gson.annotations.SerializedName;

public class StopPoint {

   @SerializedName("id")
   int id;

   @SerializedName("name")
   String name;

   @SerializedName("address")
   String address;

   @SerializedName("provinceId")
   int provinceId;

   @SerializedName("lat")
   float lat;

   @SerializedName("long")
   float longitude;

   @SerializedName("arrivalAt")
   long arrivalAt;

   @SerializedName("leaveAt")
   long leaveAt;

   @SerializedName("serviceTypeId")
   int serviceTypeId;

   @SerializedName("minCost")
   int minCost;

   @SerializedName("maxCost")
   int maxCost;

   @SerializedName("avatar")
   String avatar;

   @SerializedName("timeArrive")
   String timeArrive;

   @SerializedName("timeLeave")
   String timeLeave;

   @SerializedName("dateArrive")
   String dateArrive;

   @SerializedName("dateLeave")
   String dateLeave;

   public StopPoint() {
   }
   public String getTimeArrive() {
      return timeArrive;
   }
   public void setTimeArrive(String timeArrive) {
      this.timeArrive = timeArrive;
   }
   public String getTimeLeave() {
      return timeLeave;
   }
   public void setTimeLeave(String timeLeave) {
      this.timeLeave = timeLeave;
   }
   public String getDateArrive() {
      return dateArrive;
   }
   public void setDateArrive(String dateArrive) {
      this.dateArrive = dateArrive;
   }
   public String getDateLeave() {
      return dateLeave;
   }
   public void setDateLeave(String dateLeave) {
      this.dateLeave = dateLeave;
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
   public String getAddress() {
      return address;
   }
   public void setAddress(String address) {
      this.address = address;
   }
   public int getProvinceId() {
      return provinceId;
   }
   public void setProvinceId(int provinceId) {
      this.provinceId = provinceId;
   }
   public float getLat() {
      return lat;
   }
   public void setLat(float lat) {
      this.lat = lat;
   }
   public float getLongitude() {
      return longitude;
   }
   public void setLongitude(float longitude) {
      this.longitude = longitude;
   }
   public long getArrivalAt() {
      return arrivalAt;
   }
   public void setArrivalAt(long arrivalAt) {
      this.arrivalAt = arrivalAt;
   }
   public long getLeaveAt() {
      return leaveAt;
   }
   public void setLeaveAt(long leaveAt) {
      this.leaveAt = leaveAt;
   }
   public int getServiceTypeId() {
      return serviceTypeId;
   }
   public void setServiceTypeId(int serviceTypeId) {
      this.serviceTypeId = serviceTypeId;
   }
   public int getMinCost() {
      return minCost;
   }
   public void setMinCost(int minCost) {
      this.minCost = minCost;
   }
   public int getMaxCost() {
      return maxCost;
   }
   public void setMaxCost(int maxCost) {
      this.maxCost = maxCost;
   }
   public String getAvatar() {
      return avatar;
   }
   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }
}
