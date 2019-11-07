package com.example.hornpub_travel_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hornpub_travel_app.R;

public class SignUpActivity extends AppCompatActivity {
   EditText editTextFullName, editTextEmail, editTextPhone, editTextPassWord, editTextConfirmPass;
   Button buttonSignUp;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_sign_up);
      mapping();
      buttonSignUp.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            checkData();
         }

      });
   }

   private void mapping() {
      editTextFullName = findViewById(R.id.editTextFullName);
      editTextEmail = findViewById(R.id.editTextEmail);
      editTextPhone = findViewById(R.id.editTextPhone);
      editTextPassWord = findViewById(R.id.editTextPass);
      editTextConfirmPass = findViewById(R.id.editTextConfirmPassWord);
      buttonSignUp = findViewById(R.id.buttonSignUp);
   }

   private void checkData() {
      String fullName, email, phone, password, confirmPass;
      fullName = editTextFullName.getText().toString();
      email = editTextEmail.getText().toString();
      phone = editTextPhone.getText().toString();
      password = editTextPassWord.getText().toString();
      confirmPass = editTextConfirmPass.getText().toString();

   }
}
