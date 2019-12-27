package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

public class ReviewTourRequest {
   @SerializedName("tourId")
   int tourId;

   @SerializedName("point")
   int point;

   @SerializedName("review")
   String review;

   public ReviewTourRequest(int tourId, int point, String review) {
      this.tourId = tourId;
      this.point = point;
      this.review = review;
   }
}
