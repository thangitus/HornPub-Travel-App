package com.ygaps.travelapp.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.model.user.UpdateUserInfoRequest;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {
   private static final String TAG = "UpdateProfile";
   Toolbar toolbar;
   EditText fullName, email, phone;
   int gender;
   TextView birthday;
   RadioGroup radioGroupGender;
   ImageButton imgCalendar;
   RadioButton radioButtonMale, radioButtonFemale;
   UpdateUserInfoRequest updateUserInfoRequest;
   Calendar myCalendar;
   Button buttonUpdate;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_update_profile);
      Intent intent = getIntent();
      updateUserInfoRequest = (UpdateUserInfoRequest) intent.getSerializableExtra("UserInfo");
      mapping();
      setData();
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      imgCalendar.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            datePicker();
         }
      });
      buttonUpdate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            getData();
            mApplication mApp;
            mApp = (mApplication) getApplicationContext();
            String token = mApp.getToken();
            APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
            Call<ResponseBody> call = apiService.updateInfo(token, updateUserInfoRequest);
            call.enqueue(new Callback<ResponseBody>() {
               @Override
               public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                  if (response.code() == 200) {
                     Log.i(TAG, "Update info success");
                     finish();
                  }
               }
               @Override
               public void onFailure(Call<ResponseBody> call, Throwable t) {
                  Log.i(TAG, "Update info fail");
               }
            });
         }
      });
   }
   private void getData() {
      updateUserInfoRequest.setFullName(fullName.getText().toString());
      updateUserInfoRequest.setEmail(email.getText().toString());
      updateUserInfoRequest.setPhone(phone.getText().toString());
      updateUserInfoRequest.setGender(1);
   }
   private void mapping() {
      toolbar = findViewById(R.id.toolbar);
      fullName = findViewById(R.id.editTextFullNameProfile);
      email = findViewById(R.id.editTextEmailProfile);
      phone = findViewById(R.id.editTextPhoneProfile);
      radioGroupGender = findViewById(R.id.radioGroupGender);
      radioButtonMale = findViewById(R.id.radioButtonMale);
      radioButtonFemale = findViewById(R.id.radioButtonFemale);
      birthday = findViewById(R.id.textViewFillBirthday);
      imgCalendar = findViewById(R.id.imageButtonCalendar);
      buttonUpdate = findViewById(R.id.buttonUpdateProfile);
   }
   private void setData() {
      fullName.setText(updateUserInfoRequest.getFullName());
      email.setText(updateUserInfoRequest.getEmail());
      phone.setText(updateUserInfoRequest.getPhone());
      radioButtonMale.setChecked(true);
   }
   private void datePicker() {
      myCalendar = Calendar.getInstance();
      int day = myCalendar.get(Calendar.DAY_OF_MONTH);
      int month = myCalendar.get(Calendar.MONTH);
      int year = myCalendar.get(Calendar.YEAR);
      DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateProfileActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
         @Override
         public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH, i2);
            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            birthday.setText(sdf.format(myCalendar.getTime()));
         }
      }, year, month, day);
      datePickerDialog.show();
   }

}
