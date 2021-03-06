package com.ygaps.travelapp.model.create_tour;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CreateTourRequest implements Parcelable {

   @SerializedName("name")
   String name;

   @SerializedName("startDate")
   String startDate;

   @SerializedName("endDate")
   String endDate;

   @SerializedName("sourceLat")
   float sourceLat;

   @SerializedName("sourceLong")
   float sourceLong;

   @SerializedName("desLat")
   float desLat;

   @SerializedName("desLong")
   float desLong;

   @SerializedName("isPrivate")
   Boolean isPrivate;

   @SerializedName("adultsoptional")
   int adults;

   @SerializedName("childs")
   int childs;

   @SerializedName("minCost")
   int minCost;

   @SerializedName("maxCost")
   int maxCost;

//   @SerializedName("avatar")
//   String avatar;

   public CreateTourRequest() {
      this.name = "";
      this.startDate = "";
      this.endDate = "";
      this.sourceLat = 0;
      this.sourceLong = 0;
      this.desLat = 0;
      this.desLong = 0;
      this.isPrivate = false;
      this.adults = 0;
      this.childs = 0;
      this.maxCost = 0;
//      this.avatar = "";
   }

   protected CreateTourRequest(Parcel in) {
      name = in.readString();
      startDate = in.readString();
      endDate = in.readString();
      sourceLat = in.readFloat();
      sourceLong = in.readFloat();
      desLat = in.readFloat();
      desLong = in.readFloat();
      byte tmpIsPrivate = in.readByte();
      isPrivate = tmpIsPrivate == 0 ? null : tmpIsPrivate == 1;
      adults = in.readInt();
      childs = in.readInt();
      minCost = in.readInt();
      maxCost = in.readInt();
   }
   public static final Creator<CreateTourRequest> CREATOR = new Creator<CreateTourRequest>() {
      @Override
      public CreateTourRequest createFromParcel(Parcel in) {
         return new CreateTourRequest(in);
      }

      @Override
      public CreateTourRequest[] newArray(int size) {
         return new CreateTourRequest[size];
      }
   };
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }
   public String getStartDate() {
      return startDate;
   }
   public void setStartDate(String startDate) {
      this.startDate = startDate;
   }
   public String getEndDate() {
      return endDate;
   }
   public void setEndDate(String endDate) {
      this.endDate = endDate;
   }
   public float getSourceLat() {
      return sourceLat;
   }
   public void setSourceLat(float sourceLat) {
      this.sourceLat = sourceLat;
   }
   public float getSourceLong() {
      return sourceLong;
   }
   public void setSourceLong(float sourceLong) {
      this.sourceLong = sourceLong;
   }
   public float getDesLat() {
      return desLat;
   }
   public void setDesLat(float desLat) {
      this.desLat = desLat;
   }
   public float getDesLong() {
      return desLong;
   }
   public void setDesLong(float desLong) {
      this.desLong = desLong;
   }
   public Boolean getPrivate() {
      return isPrivate;
   }
   public void setPrivate(Boolean aPrivate) {
      isPrivate = aPrivate;
   }
   public int getAdults() {
      return adults;
   }
   public void setAdults(int adults) {
      this.adults = adults;
   }
   public int getChilds() {
      return childs;
   }
   public void setChilds(int childs) {
      this.childs = childs;
   }
   public int getMinCost() {
      return minCost;
   }
   public void setMinCost(int minCost) {
      this.minCost = minCost;
   }
   public int getMaxCost() {
      return maxCost;
   }
   public void setMaxCost(int maxCost) {
      this.maxCost = maxCost;
   }

   @Override
   public int describeContents() {
      return 0;
   }
   @Override
   public void writeToParcel(Parcel parcel, int i) {
      parcel.writeString(name);
      parcel.writeString(startDate);
      parcel.writeString(endDate);
      parcel.writeFloat(sourceLat);
      parcel.writeFloat(sourceLong);
      parcel.writeFloat(desLat);
      parcel.writeFloat(desLong);
      parcel.writeByte((byte) (isPrivate == null ? 0 : isPrivate ? 1 : 2));
      parcel.writeInt(adults);
      parcel.writeInt(childs);
      parcel.writeInt(minCost);
      parcel.writeInt(maxCost);
   }
}
