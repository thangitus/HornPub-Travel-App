package com.ygaps.travelapp.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings.Secure;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.activity.HomeActivity;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseService extends FirebaseMessagingService {
   private static final String TAG = "MyFirebaseService";

   @Override
   public void onMessageReceived(RemoteMessage remoteMessage) {
      // handle a notification payload.
      if (remoteMessage.getNotification() != null) {
         Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
         sendNotification(remoteMessage.getNotification().getBody());
      }
   }

   @Override
   public void onNewToken(String token) {
      Log.d(TAG, "Refreshed token: " + token);
      sendRegistrationToServer(token);
   }

   private void sendRegistrationToServer(String token) {
      mApplication mApp;
      mApp = (mApplication) getApplicationContext();
      String authorization = mApp.getToken();
      @SuppressLint("HardwareIds")
      String deviceId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

      APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      RegisterFirebase registerFirebase = new RegisterFirebase(token, deviceId, 1, "1.0");

      Call<ResponseBody> call = apiService.registerFirebase(authorization, registerFirebase);
      call.enqueue(new Callback<ResponseBody>() {
         @Override
         public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            Log.d("Home", response.message());
         }
         @Override
         public void onFailure(Call<ResponseBody> call, Throwable t) {
         }
      });
   }

   private void sendNotification(String messageBody) {
      Intent intent = new Intent(this, HomeActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

      String channelId = getString(R.string.project_id);
      Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

      NotificationCompat.Builder notificationBuilder =
              new NotificationCompat.Builder(this, channelId)
                      .setSmallIcon(R.drawable.ic_launcher_background)
                      .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                      .setContentTitle(getString(R.string.project_id))
                      .setContentText(messageBody)
                      .setAutoCancel(true)
                      .setSound(defaultSoundUri)
                      .setContentIntent(pendingIntent)
                      .setDefaults(Notification.DEFAULT_ALL)
                      .setPriority(NotificationManager.IMPORTANCE_HIGH)
                      .addAction(new NotificationCompat.Action(
                              android.R.drawable.sym_call_missed,
                              "Cancel",
                              PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)))
                      .addAction(new NotificationCompat.Action(
                              android.R.drawable.sym_call_outgoing,
                              "OK",
                              PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)));

      NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

      // Since android Oreo notification channel is needed.
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         NotificationChannel channel = new NotificationChannel(
                 channelId,
                 "Channel human readable title",
                 NotificationManager.IMPORTANCE_DEFAULT);

         notificationManager.createNotificationChannel(channel);
      }

      notificationManager.notify(0, notificationBuilder.build());
   }
}