package com.ygaps.travelapp.model.invite_member;

public class InviteMemberRequest {
   String tourId, invitedUserId;
   Boolean isInvited;

   public InviteMemberRequest(String tourId, String invitedUserId, Boolean isInvited) {
      this.tourId = tourId;
      this.invitedUserId = invitedUserId;
      this.isInvited = isInvited;
   }
}
