package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class CurrentCoordinateRequest {
   int tourId;
   double lat;

   @SerializedName("long")
   double longtitude;
   public CurrentCoordinateRequest(int tourId, double lat, double longtitude) {
      this.tourId = tourId;
      this.lat = lat;
      this.longtitude = longtitude;
   }
}
