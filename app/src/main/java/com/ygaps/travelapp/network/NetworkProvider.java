package com.ygaps.travelapp.network;

import com.ygaps.travelapp.manager.Constants;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NetworkProvider {
   private static volatile NetworkProvider mInstance = null;
   private Retrofit retrofit;


   private NetworkProvider() {
      OkHttpClient client = new OkHttpClient.Builder().build();
      retrofit = new Retrofit.Builder()
              .baseUrl(Constants.APIEndpoint)
              .client(client)
              .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
              .build();
   }

   public Retrofit getRetrofit() {
      return retrofit;
   }

   public static NetworkProvider getInstance() {
      if (mInstance == null)
         mInstance = new NetworkProvider();
      return mInstance;
   }
}
