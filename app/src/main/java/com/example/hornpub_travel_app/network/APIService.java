package com.example.hornpub_travel_app.network;

import com.example.hornpub_travel_app.model.ListTourResponse;
import com.example.hornpub_travel_app.model.LoginRequest;
import com.example.hornpub_travel_app.model.LoginResponse;
import com.example.hornpub_travel_app.model.RegisterRequest;
import com.example.hornpub_travel_app.model.RegisterResponse;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface APIService {
   @POST("/user/login")
   Call<LoginResponse> login(@Body LoginRequest loginRequest);

   @POST("/user/register")
   Call<RegisterResponse> register(@Body RegisterRequest registerRequest);


   @GET("/tour/list")
   Call<ListTourResponse> getListTour(@Header("Authorization") String token, @QueryMap Map<String,String> params);

}
