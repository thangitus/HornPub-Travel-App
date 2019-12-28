package com.ygaps.travelapp.network;

import com.ygaps.travelapp.model.GetReviewResponse;
import com.ygaps.travelapp.model.ListTourResponse;
import com.ygaps.travelapp.model.ReviewTourRequest;
import com.ygaps.travelapp.model.SearchUserResponse;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.model.create_tour.AddStopPointRequest;
import com.ygaps.travelapp.model.create_tour.CreateTourRequest;
import com.ygaps.travelapp.model.create_tour.StopPoint;
import com.ygaps.travelapp.model.google_map.GetSuggestDestinationsRequest;
import com.ygaps.travelapp.model.google_map.SuggestedDestination;
import com.ygaps.travelapp.model.invite_member.InviteMemberRequest;
import com.ygaps.travelapp.model.login.LoginRequest;
import com.ygaps.travelapp.model.login.LoginResponse;
import com.ygaps.travelapp.model.login.PasswordRecoveryRequest;
import com.ygaps.travelapp.model.login.VerifyOtpRequest;
import com.ygaps.travelapp.model.notification.NotificationResponse;
import com.ygaps.travelapp.model.notification.ResponseInvitation;
import com.ygaps.travelapp.model.user.RegisterRequest;
import com.ygaps.travelapp.model.user.RegisterResponse;
import com.ygaps.travelapp.model.user.UpdateUserInfoRequest;
import com.ygaps.travelapp.model.user.UserInfoResponse;
import com.ygaps.travelapp.service.RegisterFirebase;

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

   @POST("/user/login/by-facebook")
   Call<LoginResponse> loginFacebook(@Body LoginRequest loginRequest);

   @POST("/user/request-otp-recovery")
   Call<ResponseBody> requestOtp(@Body PasswordRecoveryRequest passwordRecoveryRequest);

   @POST("/user/verify-otp-recovery")
   Call<ResponseBody> verifyOtp(@Body VerifyOtpRequest verifyOtpRequest);

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
   Call<Tour> getTourInfo(@Header("Authorization") String token, @Query("tourId") int tourId);

   @POST("/tour/update-tour")
   Call<ResponseBody> updateTour(@Header("Authorization") String token, @Body Tour tour);

   @GET("/tour/remove-stop-point")
   Call<ResponseBody> removeStopPoint(@Header("Authorization") String token, @Query("stopPointId") int stopPointId);

   @POST("/tour/update-stop-point")
   Call<ResponseBody> updateStopPoint(@Header("Authorization") String token, @Body int id, @Body StopPoint stopPoint);

   @POST("/user/notification/put-token")
   Call<ResponseBody> registerFirebase(@Header("Authorization") String token, @Body RegisterFirebase registerFirebase);

   @GET("/user/search")
   Call<SearchUserResponse> searchUsers(@Query("searchKey") String searchKey, @Query("pageIndex") int pageIndex, @Query("pageSize") String pageSize);

   @POST("/tour/add/member")
   Call<ResponseBody> inviteMember(@Header("Authorization") String token, @Body InviteMemberRequest inviteMemberRequest);

   @GET("/user/info")
   Call<UserInfoResponse> userInfo(@Header("Authorization") String token);

   @GET("/tour/get/review-list")
   Call<GetReviewResponse> getReview(@Header("Authorization") String token, @Query("tourId") int tourId, @Query("pageIndex") int pageIndex, @Query("pageSize") String pageSize);

   @POST("/tour/add/review")
   Call<ResponseBody> reviewTour(@Header("Authorization") String token, @Body ReviewTourRequest reviewTourRequest);

   @POST("/user/edit-info")
   Call<ResponseBody> updateInfo(@Header("Authorization") String token, @Body UpdateUserInfoRequest updateUserInfoRequest);

   @POST("/tour/suggested-destination-list")
   Call<SuggestedDestination> getSuggestedDestination(@Header("Authorization") String token, @Body GetSuggestDestinationsRequest request);

   @GET("/tour/get/invitation")
   Call<NotificationResponse> getListTourInvitation(@Header("Authorization") String token, @Query("pageIndex") int pageIndex, @Query("pageSize") int pageSize);

   @POST("/tour/response/invitation")
   Call<ResponseBody> responseInvitation(@Header("Authorization") String token, @Body ResponseInvitation responseInvitation);
}
