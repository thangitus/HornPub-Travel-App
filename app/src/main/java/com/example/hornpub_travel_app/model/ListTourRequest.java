package com.example.hornpub_travel_app.model;

import com.google.gson.annotations.SerializedName;

public class ListTourRequest {

   @SerializedName("rowPerPage")
   int rowPerPage;

   @SerializedName("pageNum")
   int pageNum;

   @SerializedName("orderBy")
   String orderBy;

   @SerializedName("isDesc")
   Boolean isDesc;

   public ListTourRequest( int rowPerPage, int pageNum, String orderBy, Boolean isDesc) {
      this.rowPerPage = rowPerPage;
      this.pageNum = pageNum;
      this.orderBy = orderBy;
      this.isDesc = isDesc;
   }

   public int getRowPerPage() {
      return rowPerPage;
   }

   public void setRowPerPage(int rowPerPage) {
      this.rowPerPage = rowPerPage;
   }

   public int getPageNum() {
      return pageNum;
   }

   public void setPageNum(int pageNum) {
      this.pageNum = pageNum;
   }

   public String getorderBy() {
      return orderBy;
   }

   public void setorderBy(String orderBy) {
      this.orderBy = orderBy;
   }

   public Boolean getIsDesc() {
      return isDesc;
   }

   public void setIsDesc(Boolean desc) {
      isDesc = desc;
   }

   public ListTourRequest(ListTourRequest listTourRequest) {
      this.rowPerPage = listTourRequest.getRowPerPage();
      this.pageNum = listTourRequest.getPageNum();
      this.orderBy = listTourRequest.getorderBy();
      this.isDesc = listTourRequest.getIsDesc();
   }
}
