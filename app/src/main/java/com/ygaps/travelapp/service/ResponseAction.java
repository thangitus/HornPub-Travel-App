package com.ygaps.travelapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;

public class ResponseAction extends BroadcastReceiver {
   private String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiIzNzMiLCJwaG9uZSI6IjA3ODc1NTkyNjciLCJlbWFpbCI6Im1pbmhoaWVuQGdtYWlsLmNvbSIsImV4cCI6MTU3ODg2MTc4ODYxNywiYWNjb3VudCI6InVzZXIiLCJpYXQiOjE1NzYyNjk3ODh9.uV1YW6nuE4ZuaLTd1xrxJaKJMnaXHEY36Aq0h3odJ_0";
   private String tourId;
   private String message;

   @Override
   public void onReceive(Context context, Intent intent) {
      tourId = intent.getStringExtra("tourId");
      message = intent.getStringExtra("message");
      String action = intent.getStringExtra("action");
      Log.e("FROM:",tourId + message);
      if(action.equals("OK")){
         AcceptInvitation();

      }

      else if(action.equals("Cancel")){
         RefuseInvitation();
         Toast.makeText(context,"Canceled invitation",Toast.LENGTH_LONG).show();
      }
      //This is used to close the notification tray
      Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
      context.sendBroadcast(it);
   }

   private void RefuseInvitation() {

   }

   private void AcceptInvitation() {
   }
}
