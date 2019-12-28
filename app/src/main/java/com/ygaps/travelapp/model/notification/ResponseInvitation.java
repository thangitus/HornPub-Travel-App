package com.ygaps.travelapp.model.notification;

public class ResponseInvitation {
   String tourId;
   Boolean isAccepted;
   public ResponseInvitation(String tourId, Boolean isAccepted) {
      this.tourId = tourId;
      this.isAccepted = isAccepted;
   }
}
