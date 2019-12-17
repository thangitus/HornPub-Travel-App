package com.example.hornpub_travel_app.model.create_tour;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AddStopPointRequest {
   @SerializedName("tourId")
   String tourId;

   @SerializedName("stopPoints")
   List<StopPoint> stopPoints;

   @SerializedName("deleteIds")
   List<String> deleteIds;

   public AddStopPointRequest() {
      stopPoints = new ArrayList<>();
   }
   public String getTourId() {
      return tourId;
   }
   public void setTourId(String tourId) {
      this.tourId = tourId;
   }
   public List<StopPoint> getStopPoints() {
      return stopPoints;
   }
   public void setStopPoints(List<StopPoint> stopPoints) {
      this.stopPoints = stopPoints;
   }
   public List<String> getDeleteIds() {
      return deleteIds;
   }
   public void setDeleteIds(List<String> deleteIds) {
      this.deleteIds = deleteIds;
   }
}
