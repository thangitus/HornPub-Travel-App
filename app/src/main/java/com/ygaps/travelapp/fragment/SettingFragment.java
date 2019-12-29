package com.ygaps.travelapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.activity.LoginActivity;
import com.ygaps.travelapp.activity.UpdateProfileActivity;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.model.user.UpdateUserInfoRequest;
import com.ygaps.travelapp.model.user.UserInfoResponse;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
   private static final String TAG = "SettingFragment";
   Button buttonLogout;
   ImageView imageViewAvt;
   TextView textViewFullName;
   LinearLayout linearLayoutEditProfile;
   String token;
   mApplication mApp;
   UserInfoResponse userInfoResponse;
   public SettingFragment() {
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_setting, container, false);
   }
   @Override
   public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapping();
      loadInfo();
      buttonLogout.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            disconnectFromFacebook();
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("LoginResponse", Context.MODE_PRIVATE);
            sharedPreferences.edit().clear().commit();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
         }
      });
      linearLayoutEditProfile.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent intent = new Intent(getActivity(), UpdateProfileActivity.class);
            UpdateUserInfoRequest updateUserInfoRequest = new UpdateUserInfoRequest(userInfoResponse.getFull_name(), userInfoResponse.getEmail(), userInfoResponse.getPhone(), 1, null);
            Bundle bundle = new Bundle();
            bundle.putSerializable("UserInfo", updateUserInfoRequest);
            intent.putExtras(bundle);
            startActivity(intent);
         }
      });
   }

   private void mapping() {
      buttonLogout = getView().findViewById(R.id.buttonLogout);
      imageViewAvt = getView().findViewById(R.id.imageViewAvatar);
      textViewFullName = getView().findViewById(R.id.textViewFullName);
      linearLayoutEditProfile = getView().findViewById(R.id.linearLayoutEditProfile);
   }
   private void loadInfo() {
      mApp = (mApplication) getActivity().getApplicationContext();
      token = mApp.getToken();
      APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      Call<UserInfoResponse> call = apiService.userInfo(token);
      call.enqueue(new Callback<UserInfoResponse>() {
         @Override
         public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
            if (response.code() == 200) {
               Log.i(TAG, "userInfo success");
               userInfoResponse = new UserInfoResponse(response.body());
               textViewFullName.setText(userInfoResponse.getFull_name());
               if (userInfoResponse.getAvatar() != null)
                  Glide.with(getContext()).load(userInfoResponse.getAvatar()).into(imageViewAvt);
            }
         }
         @Override
         public void onFailure(Call<UserInfoResponse> call, Throwable t) {
            Log.i(TAG, "userInfo failure");
         }
      });
   }
   private void disconnectFromFacebook() {
      if (AccessToken.getCurrentAccessToken() == null) {
         return; // already logged out
      }
      new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
              .Callback() {
         @Override
         public void onCompleted(GraphResponse graphResponse) {
            LoginManager.getInstance().logOut();
         }
      }).executeAsync();
   }

}
