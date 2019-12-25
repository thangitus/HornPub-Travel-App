package com.ygaps.travelapp.model.create_tour;

import com.google.gson.annotations.SerializedName;

public class CreateTourResponse {
   @SerializedName("hostId")
   String hostId;

   @SerializedName("status")
   int status;

   @SerializedName("minCost")
   int minCost;

   @SerializedName("maxCost")
   int maxCost;

   @SerializedName("startDate")
   long startDate;

   @SerializedName("endDate")
   long endDate;

   @SerializedName("adults")
   int adults;

   @SerializedName("childs")
   int childs;

   @SerializedName("sourceLat")
   int sourceLat;

   @SerializedName("sourceLong")
   int sourceLong;

   @SerializedName("desLat")
   int desLat;

   @SerializedName("desLong")
   int desLong;

   @SerializedName("id")
   int id;

   @SerializedName("isPrivate")
   Boolean isPrivate;

   @SerializedName("avatar")
   String avatar;

   public String getHostId() {
      return hostId;
   }

   public void setHostId(String hostId) {
      this.hostId = hostId;
   }

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
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

   public long getStartDate() {
      return startDate;
   }

   public void setStartDate(long startDate) {
      this.startDate = startDate;
   }

   public long getEndDate() {
      return endDate;
   }

   public void setEndDate(long endDate) {
      this.endDate = endDate;
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

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public Boolean getPrivate() {
      return isPrivate;
   }

   public void setPrivate(Boolean aPrivate) {
      isPrivate = aPrivate;
   }

   public String getAvatar() {
      return avatar;
   }

   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }

   public CreateTourResponse(CreateTourResponse createTourResponse) {
      this.hostId = createTourResponse.getHostId();
      this.status = createTourResponse.getStatus();
      this.minCost = createTourResponse.getMinCost();
      this.maxCost = createTourResponse.getMaxCost();
      this.startDate = createTourResponse.getStartDate();
      this.endDate = createTourResponse.getEndDate();
      this.adults = createTourResponse.getAdults();
      this.childs = createTourResponse.getChilds();
      this.sourceLat = createTourResponse.getSourceLat();
      this.sourceLong = createTourResponse.getSourceLong();
      this.desLat = createTourResponse.getDesLat();
      this.desLong = createTourResponse.getDesLong();
      this.id = createTourResponse.getId();
      this.isPrivate = createTourResponse.getPrivate();
      this.avatar = createTourResponse.getAvatar();
   }
}
