package com.example.hornpub_travel_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
   TextView tvSignUp;
   Intent intentToSignUp;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);
      intentToSignUp = new Intent(this, SignUpActivity.class);
      mapping();

      tvSignUp.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(intentToSignUp);
         }
      });
   }

   private void mapping() {
      tvSignUp = findViewById(R.id.textViewSignUp);
   }
}
