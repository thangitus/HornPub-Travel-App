package com.ygaps.travelapp.model.feedback_stop_point;

import com.google.gson.annotations.SerializedName;
import com.ygaps.travelapp.model.Comment;

import java.util.List;

public class FeedBackStopPoint {
   @SerializedName("feedbackList")
   List<Comment> comments;

   public List<Comment> getComments() {
      return comments;
   }
   public void setComments(List<Comment> comments) {
      this.comments = comments;
   }
}
