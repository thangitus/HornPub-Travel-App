package com.example.hornpub_travel_app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListTourResponse {
   @SerializedName("total")
   String total;

   @SerializedName("tours")
   List<Tour> tours;

   public ListTourResponse(String total, List<Tour> tours) {
      this.total = total;
      this.tours = tours;
   }

   public String getTotal() {
      return total;
   }

   public void setTotal(String total) {
      this.total = total;
   }

   public List<Tour> getTours() {
      return tours;
   }

   public void setTours(List<Tour> tours) {
      this.tours = tours;
   }

   public ListTourResponse(ListTourResponse listTourResponse) {
      this.total = listTourResponse.getTotal();
      this.tours = listTourResponse.getTours();
   }
}
