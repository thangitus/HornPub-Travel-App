package com.example.hornpub_travel_app.network;

import com.example.hornpub_travel_app.manager.Constants;
import com.example.hornpub_travel_app.model.LoginResponse;
import com.example.hornpub_travel_app.model.RegisterResponse;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NetworkProvider {
   private static volatile NetworkProvider mInstance = null;
   private Retrofit retrofit;
   private RegisterResponse registerResponse;
   private LoginResponse loginResponse;

   public void setLoginResponse(LoginResponse loginResponse) {
      this.loginResponse = loginResponse;
   }

   public void setRegisterResponse(RegisterResponse registerResponse) {
      this.registerResponse = registerResponse;
   }

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
