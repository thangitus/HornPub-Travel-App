package com.ygaps.travelapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.fragment.VerifyOtpDialogFragment;
import com.ygaps.travelapp.model.login.PasswordRecoveryRequest;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassWordActivity extends AppCompatActivity {
   LinearLayout viaSMS, viaEmail;
   EditText editTextInput;
   ImageView imgSMS, imgEmail;
   TextView tvSMS, tvEmail;
   Button buttonSubmit;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_forgot_pass_word);
      mapping();

      final APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      viaEmail.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            choiceEmail();
         }
      });
      viaSMS.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            choiceSMS();
         }
      });
      buttonSubmit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            String value = editTextInput.getText().toString();
            if (value.equals("")) {
               Toast.makeText(ForgotPassWordActivity.this, "Email không được rỗng", Toast.LENGTH_SHORT).show();
               return;
            }
            PasswordRecoveryRequest passwordRecoveryRequest = new PasswordRecoveryRequest("email", value);
            Call<ResponseBody> call = apiService.requestOtp(passwordRecoveryRequest);
            call.enqueue(new Callback<ResponseBody>() {
               @Override
               public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                  if (response.code() == 404)
                     Toast.makeText(ForgotPassWordActivity.this, "Email không tồn tại", Toast.LENGTH_SHORT).show();
                  else if (response.code() == 200) {
                     String string = null;
                     try {
                        string = response.body().string();
                        JSONObject jsonObject = new JSONObject(string);
                        string = jsonObject.getString("userId");
                        DialogFragment dialogFragment = VerifyOtpDialogFragment.newInstance(string);
                        dialogFragment.show(getSupportFragmentManager(), "dialog");
                     } catch (IOException | JSONException e) {
                        e.printStackTrace();
                     }
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
      viaSMS = findViewById(R.id.viaSMS);
      viaEmail = findViewById(R.id.viaEmail);
      editTextInput = findViewById(R.id.editTextVia);
      imgSMS = findViewById(R.id.imgViaSMS);
      imgEmail = findViewById(R.id.imgViaEmail);
      tvEmail = findViewById(R.id.textViewViaEmail);
      tvSMS = findViewById(R.id.textViewViaSms);
      buttonSubmit = findViewById(R.id.buttonSubmit);
   }

   private void choiceSMS() {
      imgSMS.setImageResource(R.drawable.icons8_sms_blue_96);
      tvSMS.setTextColor(getResources().getColor(R.color.colorBlue));
      imgEmail.setImageResource(R.drawable.icons8_mail_gray_96);
      tvEmail.setTextColor(getResources().getColor(R.color.colorGray));
      editTextInput.setHint(getResources().getString(R.string.enterPhone));
   }

   private void choiceEmail() {
      imgEmail.setImageResource(R.drawable.icons8_mail_blue_96);
      tvEmail.setTextColor(getResources().getColor(R.color.colorBlue));
      imgSMS.setImageResource(R.drawable.icons8_sms_gray_96);
      tvSMS.setTextColor(getResources().getColor(R.color.colorGray));
      editTextInput.setHint(getResources().getString(R.string.enterEmail));
   }
}
