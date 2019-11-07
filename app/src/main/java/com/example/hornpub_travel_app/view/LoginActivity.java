package com.example.hornpub_travel_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.network.APIService;
import com.example.hornpub_travel_app.network.NetworkProvider;

public class LoginActivity extends AppCompatActivity {
   TextView tvSignUp, tvForgotPassWord;
   Intent intentToSignUp, intentToForgotPassWord;
   APIService apiService;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);
      intentToSignUp = new Intent(this, SignUpActivity.class);
      intentToForgotPassWord = new Intent(this, ForgotPassWordActivity.class);
      apiService= NetworkProvider.getInstance().getRetrofit().create(APIService.class);

      mapping();

      tvSignUp.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(intentToSignUp);
         }
      });
      tvForgotPassWord.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(intentToForgotPassWord);
         }
      });
   }

   private void mapping() {
      tvForgotPassWord = findViewById(R.id.textViewForgotPass);
      tvSignUp = findViewById(R.id.textViewSignUp);
   }
}
