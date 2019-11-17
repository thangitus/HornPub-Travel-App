package com.example.hornpub_travel_app.model;

import com.google.gson.annotations.SerializedName;

public class CreateTourRequest {
   @SerializedName("name")
   String name;

   @SerializedName("startDate")
   int startDate;

   @SerializedName("endDate")
   int endDate;

   @SerializedName("sourceLat")
   int sourceLat;

   @SerializedName("sourceLong")
   int sourceLong;

   @SerializedName("desLat")
   int desLat;


   @SerializedName("desLong")
   int desLong;

   @SerializedName("isPrivate")
   Boolean isPrivate;

   @SerializedName("adultsoptional")
   int adults;

   @SerializedName("childs")
   int childs;

   @SerializedName("minCost")
   int minCost;

   @SerializedName("maxCost")
   int maxCost;

   @SerializedName("avatar")
   String avatar;

   public CreateTourRequest(String name, int startDate, int endDate, int sourceLat, int sourceLong, int desLat, int desLong, Boolean isPrivate, int adults, int childs, int minCost, int maxCost, String avatar) {
      this.name = name;
      this.startDate = startDate;
      this.endDate = endDate;
      this.sourceLat = sourceLat;
      this.sourceLong = sourceLong;
      this.desLat = desLat;
      this.desLong = desLong;
      this.isPrivate = isPrivate;
      this.adults = adults;
      this.childs = childs;
      this.minCost = minCost;
      this.maxCost = maxCost;
      this.avatar = avatar;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getStartDate() {
      return startDate;
   }

   public void setStartDate(int startDate) {
      this.startDate = startDate;
   }

   public int getEndDate() {
      return endDate;
   }

   public void setEndDate(int endDate) {
      this.endDate = endDate;
   }

   public int getSourceLat() {
      return sourceLat;
   }

   public void setSourceLat(int sourceLat) {
      this.sourceLat = sourceLat;
   }

   public int getSourceLong() {
      return sourceLong;
   }

   public void setSourceLong(int sourceLong) {
      this.sourceLong = sourceLong;
   }

   public int getDesLat() {
      return desLat;
   }

   public void setDesLat(int desLat) {
      this.desLat = desLat;
   }

   public int getDesLong() {
      return desLong;
   }

   public void setDesLong(int desLong) {
      this.desLong = desLong;
   }

   public Boolean getPrivate() {
      return isPrivate;
   }

   public void setPrivate(Boolean aPrivate) {
      isPrivate = aPrivate;
   }

   public int getAdults() {
      return adults;
   }

   public void setAdults(int adults) {
      this.adults = adults;
   }

   public int getChilds() {
      return childs;
   }

   public void setChilds(int childs) {
      this.childs = childs;
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
