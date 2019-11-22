package com.example.hornpub_travel_app.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hornpub_travel_app.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateTourActivity extends AppCompatActivity {
   private static final int REQUEST_CODE = 1;
   Button buttonCreate;
   EditText edtTourName, edtAdults, edtChildren, edtMinCost, edtMaxCost;
   TextView tvIMG, tvStartDay, tvEndDay;
   RadioGroup radioGroup;
   ImageButton buttonStartDate, buttonEndDate;
   Toolbar toolbar;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create_tour);
      Intent intent = getIntent();
      mapping();
      toolbar = findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
            if(checkData()){
            openCreateStopPointActivity();}
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
      radioGroup = findViewById(R.id.layoutRadio);
      buttonStartDate = findViewById(R.id.buttonCalendarStart);
      buttonEndDate = findViewById(R.id.buttonCalendarEnd);
   }

   private void openCreateStopPointActivity() {
      Intent intent = new Intent(this, CreateStopPointActivity.class);
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

   private boolean checkData(){
      if(edtTourName.length()<1){
         edtTourName.setError("Tour name không được rỗng ! ");
         return false;
      }
      if(tvStartDay.length() < 1 ){
         tvStartDay.setError("Start date không được rỗng ! ");
         return false;
      }
      if(tvEndDay.length()<1){
         tvEndDay.setError("End date không được rông ! ");
      }
      return true;
   }
}
