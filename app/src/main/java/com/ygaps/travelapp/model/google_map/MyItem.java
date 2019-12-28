package com.ygaps.travelapp.model.google_map;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyItem implements ClusterItem {
   private LatLng mPosition;
   private String mTitle;
   private String mSnippet;
   private int serviceTypeId;

   public int getServiceTypeId() {
      return serviceTypeId;
   }
   public MyItem(double lat, double lng, int serviceTypeId) {
      mPosition = new LatLng(lat, lng);
      this.serviceTypeId = serviceTypeId;
   }

   public MyItem(double lat, double lng, String title, String snippet) {
      mPosition = new LatLng(lat, lng);
      mTitle = title;
      mSnippet = snippet;
   }

   @Override
   public LatLng getPosition() {
      return mPosition;
   }

   @Override
   public String getTitle() {
      return mTitle;
   }

   @Override
   public String getSnippet() {
      return mSnippet;
   }
}
