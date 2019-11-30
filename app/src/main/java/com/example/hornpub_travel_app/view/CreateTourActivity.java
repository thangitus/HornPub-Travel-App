package com.example.hornpub_travel_app.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.application.mApplication;
import com.example.hornpub_travel_app.model.create_tour.CreateTourRequest;
import com.example.hornpub_travel_app.model.create_tour.CreateTourResponse;
import com.example.hornpub_travel_app.network.APIService;
import com.example.hornpub_travel_app.network.NetworkProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTourActivity extends AppCompatActivity {
   private static final int REQUEST_CODE = 1;
   private static final int SELECT_IMAGE = 2;
   private static final String TAG = "CreateTourActivity";
   Button buttonCreate;
   EditText edtTourName, edtAdults, edtChildren, edtMinCost, edtMaxCost;
   TextView tvIMG, tvStartDay, tvEndDay;
   RadioButton radioButton;
   ImageButton buttonStartDate, buttonEndDate, buttonAddImg;
   Toolbar toolbar;
   String token;
   CreateTourResponse createTourResponse;
   CreateTourRequest createTourRequest;
   mApplication mApp;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create_tour);
      mApp = (mApplication) getApplicationContext();

      Intent intent = getIntent();
      mapping();
      token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI3MSIsInBob25lIjoiMDE4NzYyMTQzNCIsImVtYWlsIjoibmhhdHJhbmdAZ21haWwuY29tIiwiZXhwIjoxNTc2NDA2NzIwNzQ2LCJhY2NvdW50IjoidXNlciIsImlhdCI6MTU3MzgxNDcyMH0.at7-CUH0I1nwH6Dlq9II4gsvaJ5WGUSKXzNNtvYjG-U";
      toolbar = findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      buttonAddImg.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
         }
      });
      buttonStartDate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            datePicker(tvStartDay);
         }
      });
      buttonEndDate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            datePicker(tvEndDay);
         }
      });
      buttonCreate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if (checkData()) {
               getData();
               openCreateStopPointActivity();
            }
         }
      });

   }

   private void mapping() {
      buttonCreate = findViewById(R.id.buttonCreate);
      edtTourName = findViewById(R.id.editTextTourName);
      tvStartDay = findViewById(R.id.textViewStartDate);
      tvEndDay = findViewById(R.id.textViewEndDate);
      edtAdults = findViewById(R.id.editTextAdults);
      edtChildren = findViewById(R.id.editTextChildren);
      edtMinCost = findViewById(R.id.editTextMinCost);
      edtMaxCost = findViewById(R.id.editTextMaxCost);
      tvIMG = findViewById(R.id.textViewIMG);
      buttonStartDate = findViewById(R.id.buttonCalendarStart);
      buttonEndDate = findViewById(R.id.buttonCalendarEnd);
      radioButton = findViewById(R.id.radioButtonPrivate);
      buttonAddImg = findViewById(R.id.buttonAddImg);
   }

   private void openCreateStopPointActivity() {
      Intent intent = new Intent(this, CreateStopPointActivity.class);
      intent.putExtra("createTourRequest", createTourRequest);
      startActivityForResult(intent, REQUEST_CODE);
   }

   private void datePicker(final TextView textView) {
      final Calendar myCalendar = Calendar.getInstance();
      int day = myCalendar.get(Calendar.DAY_OF_MONTH);
      int month = myCalendar.get(Calendar.MONTH);
      int year = myCalendar.get(Calendar.YEAR);
      DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTourActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
         @Override
         public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH, i2);
            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            textView.setText(sdf.format(myCalendar.getTime()));
            textView.invalidate();
            textView.requestLayout();
         }
      }, year, month, day);
      datePickerDialog.show();
   }

   private boolean checkData() {
      if (edtTourName.length() < 1) {
         Toast.makeText(this, "Tour name không được rỗng!", Toast.LENGTH_SHORT).show();
         return false;
      }
      if (tvStartDay.length() < 1) {
         Toast.makeText(this, "Start date không được rỗng!", Toast.LENGTH_SHORT).show();
         return false;
      }
      if (tvEndDay.length() < 1) {
         Toast.makeText(this, "End date không được rỗng!", Toast.LENGTH_SHORT).show();
         return false;
      }
      return true;
   }

   private long convertDateToMillisecond(String string) {
      @SuppressLint("SimpleDateFormat")
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
      Date date = new Date();
      try {
         date = formatter.parse(string);
      } catch (ParseException e) {
         e.printStackTrace();
      }
      return date.getTime();
   }

   private void getData() {
      createTourRequest = new CreateTourRequest();
      createTourRequest.setName(edtTourName.getText().toString());
      createTourRequest.setStartDate(convertDateToMillisecond(tvStartDay.getText().toString()));
      createTourRequest.setEndDate(convertDateToMillisecond(tvEndDay.getText().toString()));
      if (!edtAdults.getText().toString().equals(""))
         createTourRequest.setAdults(Integer.valueOf(edtAdults.getText().toString()));
      if (!edtChildren.getText().toString().equals(""))
         createTourRequest.setChilds(Integer.valueOf(edtChildren.getText().toString()));
      if (!edtMinCost.getText().toString().equals(""))
         createTourRequest.setMinCost(Integer.valueOf(edtMinCost.getText().toString()));
      if (!edtMaxCost.getText().toString().equals(""))
         createTourRequest.setMaxCost(Integer.valueOf(edtMaxCost.getText().toString()));
      createTourRequest.setPrivate(radioButton.isChecked());
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == SELECT_IMAGE) {
         if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
               try {
                  Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                  startThreadDecodeBitmap(bitmap);
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
         } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
         }

      }
   }

   private void startThreadDecodeBitmap(final Bitmap bitmap) {
      new Thread(new Runnable() {
         @Override
         public void run() {
            Log.wtf(TAG, "Start decode");
            Bitmap bitmapDecode;
            bitmapDecode = Bitmap.createScaledBitmap(bitmap, (bitmap.getWidth()), (bitmap.getHeight()), true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmapDecode.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            mApp.setAvatarBase64(Base64.encodeToString(bytes, Base64.NO_WRAP));
         }
      }).start();
   }
}
