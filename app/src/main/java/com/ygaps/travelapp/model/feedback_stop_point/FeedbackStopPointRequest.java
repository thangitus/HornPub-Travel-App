package com.ygaps.travelapp.model.feedback_stop_point;

public class FeedbackStopPointRequest {
   int serviceId,point;
   String feedback;
   public FeedbackStopPointRequest(int serviceId, int point, String feedback) {
      this.serviceId = serviceId;
      this.point = point;
      this.feedback = feedback;
   }
}
