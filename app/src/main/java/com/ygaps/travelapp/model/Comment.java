package com.ygaps.travelapp.model;

public class Comment {
   String id, name, review, avatar, createdOn, feedback;
   int point;
   public Comment(Comment comment) {
      this.id = comment.getId();
      this.name = comment.getName();
      this.review = comment.getReview();
      this.avatar = comment.getAvatar();
      this.createdOn = comment.getCreatedOn();
      this.point = comment.getPoint();
      this.feedback = comment.getFeedback();
   }
   public String getFeedback() {
      return feedback;
   }
   public void setFeedback(String feedback) {
      this.feedback = feedback;
   }
   public String getId() {
      return id;
   }
   public void setId(String id) {
      this.id = id;
   }
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getReview() {
      return review;
   }
   public void setReview(String review) {
      this.review = review;
   }
   public String getAvatar() {
      return avatar;
   }
   public void setAvatar(String avatar) {
      this.avatar = avatar;
   }
   public String getCreatedOn() {
      return createdOn;
   }
   public void setCreatedOn(String createdOn) {
      this.createdOn = createdOn;
   }
   public int getPoint() {
      return point;
   }
   public void setPoint(int point) {
      this.point = point;
   }
}
