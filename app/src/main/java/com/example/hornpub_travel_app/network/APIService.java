package com.example.hornpub_travel_app.network;

import com.example.hornpub_travel_app.model.LoginRequest;
import com.example.hornpub_travel_app.model.LoginResponse;
import com.example.hornpub_travel_app.model.RegisterRequest;
import com.example.hornpub_travel_app.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {
   @POST("/user/login")
   Call<LoginResponse> login(@Body LoginRequest loginRequest);

   @POST("/user/register")
   Call<RegisterResponse> login(@Body RegisterRequest registerRequest);

}
