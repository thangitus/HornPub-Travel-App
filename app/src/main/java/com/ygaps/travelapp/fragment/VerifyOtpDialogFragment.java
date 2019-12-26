package com.ygaps.travelapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.activity.LoginActivity;
import com.ygaps.travelapp.model.login.VerifyOtpRequest;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyOtpDialogFragment extends DialogFragment {
   Button buttonSubmit;
   EditText editTextOTP, editTextPass1, editTextPass2;
   String userId;
   Intent intent;
   public VerifyOtpDialogFragment() {

   }
   public static VerifyOtpDialogFragment newInstance(String userId) {
      VerifyOtpDialogFragment fragment = new VerifyOtpDialogFragment();
      fragment.setData(userId);
      return fragment;
   }
   private void setData(String userId) {
      this.userId = userId;
   }
   @Override
   public void onStart() {
      super.onStart();
      getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
   }
   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_dialog_verify_otp, container, false);
   }
   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapping();
      intent = new Intent(getActivity(), LoginActivity.class);
      buttonSubmit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if (editTextOTP.getText().toString().equals("")) {
               Toast.makeText(getActivity(), "Vui lòng nhập OTP", Toast.LENGTH_SHORT).show();
               return;
            }
            if (!checkPassword(editTextPass1.getText().toString(), editTextPass2.getText().toString()))
               return;
            VerifyOtpRequest verifyOtpRequest = new VerifyOtpRequest(userId, editTextPass1.getText().toString(), editTextOTP.getText().toString());
            APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
            Call<ResponseBody> call = apiService.verifyOtp(verifyOtpRequest);
            call.enqueue(new Callback<ResponseBody>() {
               @Override
               public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                  if (response.code() == 200) {
                     Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                     startActivity(intent);
                  } else {
                     Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                     getDialog().dismiss();
                  }
               }
               @Override
               public void onFailure(Call<ResponseBody> call, Throwable t) {

               }
            });
         }
      });
   }
   private void mapping() {
      buttonSubmit = getView().findViewById(R.id.buttonSubmitOTP);
      editTextOTP = getView().findViewById(R.id.editTextOTP);
      editTextPass1 = getView().findViewById(R.id.editTextPassWordOTP);
      editTextPass2 = getView().findViewById(R.id.editTextConfirmPassWordOTP);
   }
   private Boolean checkPassword(String password, String confirmPass) {
      if (password.length() < 5) {
         editTextPass1.setError("Mật khẩu quá ngắn, vui lòng nhập thêm");
         return false;
      }
      if (confirmPass.length() < 1) {
         editTextPass2.setError("Vui lòng nhập xác nhận mật khẩu");
         return false;
      }
      if (!password.equals(confirmPass)) {
         editTextPass2.setError("Xác nhận mật khẩu không đúng");
         return false;
      } else return true;
   }
}
