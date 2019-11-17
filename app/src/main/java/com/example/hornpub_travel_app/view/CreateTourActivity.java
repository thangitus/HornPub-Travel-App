package com.example.hornpub_travel_app.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hornpub_travel_app.R;

public class CreateTourActivity extends AppCompatActivity {
   Button create;
   Intent intentToMap;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create_tour);
//      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      create = findViewById(R.id.button);
      intentToMap = new Intent(this, mapActivity.class);

      create.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            startActivity(intentToMap);
         }
      });

   }
}
