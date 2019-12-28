package com.ygaps.travelapp.model.google_map;

import com.google.gson.annotations.SerializedName;

public class Coordinate {
   double lat;

   @SerializedName("long")
   double longtitude;

   public Coordinate(double lat, double longtitude) {
      this.lat = lat;
      this.longtitude = longtitude;
   }
}
