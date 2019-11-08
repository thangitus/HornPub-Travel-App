package com.example.hornpub_travel_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ListTourActivity extends AppCompatActivity {
   Intent intent;
   String token;
   TextView textView;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_list_tour);
      intent = getIntent();
      token = intent.getStringExtra("token");
      textView = findViewById(R.id.textView);
      textView.setText("token: " + token);
   }
}
