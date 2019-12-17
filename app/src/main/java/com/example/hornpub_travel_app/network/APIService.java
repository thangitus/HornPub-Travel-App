package com.example.hornpub_travel_app.network;

import com.example.hornpub_travel_app.model.ListTourResponse;
import com.example.hornpub_travel_app.model.Tour;
import com.example.hornpub_travel_app.model.create_tour.AddStopPointRequest;
import com.example.hornpub_travel_app.model.create_tour.CreateTourRequest;
import com.example.hornpub_travel_app.model.user.LoginRequest;
import com.example.hornpub_travel_app.model.user.LoginResponse;
import com.example.hornpub_travel_app.model.user.RegisterRequest;
import com.example.hornpub_travel_app.model.user.RegisterResponse;

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
   Call<ListTourResponse> getListTour(@Header("Authorization") String token, @QueryMap Map<String, String> params);

   @POST("/tour/create")
   Call<ResponseBody> sendCreateTourRequest(@Header("Authorization") String token, @Body CreateTourRequest createTourRequest);

   @POST("/tour/set-stop-points")
   Call<ResponseBody> addStopPoint(@Header("Authorization") String token, @Body AddStopPointRequest addStopPointRequest);

   @GET("/tour/history-user")
   Call<ListTourResponse> getHistory(@Header("Authorization") String token, @QueryMap Map<String, Number> params);

   @GET("/tour/info")
   Call<ResponseBody> getTourInfo(@Header("Authorization") String token, @Query("tourId") String tourId);

   @POST("/tour/update-tour")
   Call<ResponseBody> updateTour(@Header("Authorization") String token, @Body Tour tour);
}
