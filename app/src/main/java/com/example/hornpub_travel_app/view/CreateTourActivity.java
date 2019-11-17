package com.example.hornpub_travel_app.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hornpub_travel_app.R;

public class CreateTourActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create_tour);
//      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
   }
}
