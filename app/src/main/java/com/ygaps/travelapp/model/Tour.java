package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;
import com.ygaps.travelapp.model.create_tour.StopPoint;
import com.ygaps.travelapp.model.user.User;

import java.io.Serializable;
import java.util.List;

public class Tour implements Serializable {
   @SerializedName("id")
   int id;

   @SerializedName("status")
   int status;

   @SerializedName("name")
   String name;

   @SerializedName("minCost")
   String minCost;

   @SerializedName("maxCost")
   String maxCost;

   @SerializedName("startDate")
   String startDate;

   @SerializedName("endDate")
   String endDate;

   @SerializedName("adults")
   int adults;

   @SerializedName("childs")
   int childs;

   @SerializedName("isPrivate")
   Boolean isPrivate;

   @SerializedName("avatar")
   String avatar;

   @SerializedName("stopPoints")
   List<StopPoint> stopPoints;

   @SerializedName("members")
   List<User> members;

   @SerializedName("hostId")
   String hostId;


   public Boolean getPrivate() {
      return isPrivate;
   }
   public void setPrivate(Boolean aPrivate) {
      isPrivate = aPrivate;
   }
   public String getHostId() {
      return hostId;
   }
   public void setHostId(String hostId) {
      this.hostId = hostId;
   }
   public Tour(int id, int status, String name, String minCost, String maxCost, String startDate, String endDate, int adults, int childs, Boolean isPrivate, String avatar,String hostId) {
      this.id = id;
      this.status = status;
      this.name = name;
      this.minCost = minCost;
      this.maxCost = maxCost;
      this.startDate = startDate;
      this.endDate = endDate;
      this.adults = adults;
      this.childs = childs;
      this.isPrivate = isPrivate;
      this.avatar = avatar;
      this.hostId=hostId;
   }
   public Tour(Tour tour) {
      this.id = tour.getId();
      this.status = tour.getStatus();
      this.name = tour.getName();
      this.minCost = tour.getMinCost();
      this.maxCost = tour.getMaxCost();
      this.startDate = tour.getStartDate();
      this.endDate = tour.getEndDate();
      this.adults = tour.getAdults();
      this.childs = tour.getChilds();
      this.isPrivate = tour.getIsPrivate();
      this.avatar = tour.getAvatar();
      this.stopPoints = tour.getStopPoints();
      this.members = tour.getMembers();
   }
   public List<User> getMembers() {
      return members;
   }
   public void setMembers(List<User> members) {
      this.members = members;
   }
   public List<StopPoint> getStopPoints() {
      return stopPoints;
   }
   public void setStopPoints(List<StopPoint> stopPoints) {
      this.stopPoints = stopPoints;
   }
   public int getId() {
      return id;
   }
   public void setId(int id) {
      this.id = id;
   }
   public int getStatus() {
      return status;
   }
   public void setStatus(int status) {
      this.status = status;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getMinCost() {
      return minCost;
   }
   public void setMinCost(String minCost) {
      this.minCost = minCost;
   }
   public String getMaxCost() {
      return maxCost;
   }
   public void setMaxCost(String maxCost) {
      this.maxCost = maxCost;
   }
   public String getStartDate() {
      return startDate;
   }
   public void setStartDate(String startDate) {
      this.startDate = startDate;
   }
   public String getEndDate() {
      return endDate;
   }
   public void setEndDate(String endDate) {
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
   public Boolean getIsPrivate() {
      return isPrivate;
   }
   public void setIsPrivate(Boolean isPrivate) {
      this.isPrivate = isPrivate;
   }
   public String getAvatar() {
      return avatar;
   }
   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }
}
