package com.ygaps.travelapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetReviewResponse {
   @SerializedName("reviewList")
   List<Comment> reviewList;

   public GetReviewResponse(List<Comment> reviewList) {
      this.reviewList = reviewList;
   }
   public List<Comment> getReviewList() {
      return reviewList;
   }
   public void setReviewList(List<Comment> reviewList) {
      this.reviewList = reviewList;
   }

   public GetReviewResponse(GetReviewResponse getReviewResponse) {
      this.reviewList = getReviewResponse.getReviewList();
   }
}
