package com.ygaps.travelapp.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.DateTimeConvert;
import com.ygaps.travelapp.model.notification.Notification;
import com.ygaps.travelapp.model.notification.ResponseListener;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
   List<Notification> notificationList;
   Context context;
   ResponseListener responseListener;

   public NotificationAdapter(List<Notification> notificationList, Context context, ResponseListener responseListener) {
      this.notificationList = notificationList;
      this.context = context;
      this.responseListener = responseListener;
   }

   @NonNull
   @Override
   public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(context);
      View itemView = inflater.inflate(R.layout.notification_item, parent, false);
      NotificationAdapter.MyViewHolder viewHolder = new NotificationAdapter.MyViewHolder(itemView);
      return viewHolder;
   }

   @Override
   public void onBindViewHolder(@NonNull final NotificationAdapter.MyViewHolder holder, int position) {
      Notification notification = notificationList.get(position);
      String notificationText;

      if (notification.getHostName() == null || notification.getHostName().equals(""))
         notificationText = "Null";
      else
         notificationText = "<b>" + notification.getHostName() + "</b>";
      notificationText = notificationText + " sent you an invitation for tour ";

      if (notification.getName() == null || notification.getName().equals(""))
         notificationText = notificationText + " Null ";
      else
         notificationText = notificationText + "<b> " + notification.getName() + " </b>";
      holder.textViewNotification.setText(Html.fromHtml(notificationText));

      if (notification.getCreatedOn() == null || notification.getCreatedOn().equals(""))
         holder.textViewCreatedOn.setText("Null");
      holder.textViewCreatedOn.setText(DateTimeConvert.MillisecondToDate(notification.getCreatedOn()));

      if (notification.getHostAvatar() != null && !notification.getHostAvatar().equals(""))
         Glide.with(context).load(notification.getHostAvatar()).into(holder.avt);
      holder.buttonConfirm.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            responseListener.onResponse(true, holder.getAdapterPosition());
         }
      });
      holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            responseListener.onResponse(false, holder.getAdapterPosition());
         }
      });
   }

   @Override
   public int getItemCount() {
      return notificationList.size();
   }

   class MyViewHolder extends RecyclerView.ViewHolder {
      TextView textViewCreatedOn, textViewNotification;
      ImageView avt;
      Button buttonConfirm, buttonDelete;
      View view;
      public MyViewHolder(@NonNull View itemView) {
         super(itemView);
         this.view = itemView;
         avt = itemView.findViewById(R.id.imageViewAvatar);
         textViewCreatedOn = itemView.findViewById(R.id.textViewCreatedOn);
         textViewNotification = itemView.findViewById(R.id.textViewNotification);
         buttonConfirm = itemView.findViewById(R.id.buttonConfirm);
         buttonDelete = itemView.findViewById(R.id.buttonDeleteNotification);
      }
   }
}
