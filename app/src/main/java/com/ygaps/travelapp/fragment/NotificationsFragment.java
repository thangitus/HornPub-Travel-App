package com.ygaps.travelapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.NotificationAdapter;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.model.notification.Notification;
import com.ygaps.travelapp.model.notification.NotificationResponse;
import com.ygaps.travelapp.model.notification.ResponseInvitation;
import com.ygaps.travelapp.model.notification.ResponseListener;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment implements ResponseListener {
   private static final String TAG = "NotificationsFragment";
   RecyclerView recyclerView;
   List<Notification> notificationList;
   NotificationResponse notificationResponse;
   TextView textViewEmpty;
   NotificationAdapter adapter;
   public NotificationsFragment() {
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_notifications, container, false);
   }
   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      getNotifications();
      recyclerView = getView().findViewById(R.id.myRecyclerViewNotification);
      textViewEmpty = getView().findViewById(R.id.textViewEmptyNotification);
   }

   private void getNotifications() {
      mApplication mApp = (mApplication) getContext().getApplicationContext();
      APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      Call<NotificationResponse> call = apiService.getListTourInvitation(mApp.getToken(), 1, 100);
      call.enqueue(new Callback<NotificationResponse>() {
         @Override
         public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
            if (response.code() == 200) {
               notificationResponse = new NotificationResponse(response.body());
               notificationList = notificationResponse.getNotificationList();
               if (notificationList.size() == 0) {
                  textViewEmpty.setText("There are no notification yet");
                  return;
               }
               textViewEmpty.setVisibility(View.GONE);
               createRecyclerView();
            }
         }
         @Override
         public void onFailure(Call<NotificationResponse> call, Throwable t) {

         }
      });
   }
   private void createRecyclerView() {
      adapter = new NotificationAdapter(notificationList, getContext(), this);
      recyclerView.setAdapter(adapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
   }

   @Override
   public void onResponse(Boolean isAccept, final int pos) {
      ResponseInvitation responseInvitation = new ResponseInvitation(notificationList.get(pos).getId(), isAccept);
      mApplication mApplication = (com.ygaps.travelapp.application.mApplication) getContext().getApplicationContext();
      APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      Call<ResponseBody> call = apiService.responseInvitation(mApplication.getToken(), responseInvitation);
      call.enqueue(new Callback<ResponseBody>() {
         @Override
         public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.code() == 200)
               Log.d(TAG, "Success");
            notificationList.remove(pos);
            adapter.notifyItemRemoved(pos);
         }
         @Override
         public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.d(TAG, "onFailure");

         }
      });
   }
}
