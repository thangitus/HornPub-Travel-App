package com.example.hornpub_travel_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.model.LoginRequest;
import com.example.hornpub_travel_app.model.RegisterRequest;
import com.example.hornpub_travel_app.model.RegisterResponse;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
   EditText editTextFullName, editTextEmail, editTextPhone, editTextPassWord, editTextConfirmPass;
   Button buttonSignUp;
   RegisterRequest registerRequest;
   RegisterResponse registerResponse;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_sign_up);
      mapping();
      buttonSignUp.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if (checkData()) sendRegisterRequest(registerRequest);

         }

      });
   }

   private void sendRegisterRequest(RegisterRequest registerRequest) {

   }

   private void mapping() {
      editTextFullName = findViewById(R.id.editTextFullName);
      editTextEmail = findViewById(R.id.editTextEmail);
      editTextPhone = findViewById(R.id.editTextPhone);
      editTextPassWord = findViewById(R.id.editTextPassWord);
      editTextConfirmPass = findViewById(R.id.editTextConfirmPassWord);
      buttonSignUp = findViewById(R.id.buttonSignUp);
   }

   private Boolean checkData() {
      String fullName, email, phone, password, confirmPass;
      fullName = editTextFullName.getText().toString();
      email = editTextEmail.getText().toString();
      phone = editTextPhone.getText().toString();
      password = editTextPassWord.getText().toString();
      confirmPass = editTextConfirmPass.getText().toString();

      if (!checkPassword(password, confirmPass)) return false;

      registerRequest = new RegisterRequest(email, password, phone, fullName, null, null, null);
      return true;
   }

   private Boolean checkEmail(String email) {
      if (email.length() < 1) {
         editTextEmail.setError("Email không được rỗng");
         return false;
      }
      String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
              "[a-zA-Z0-9_+&*-]+)*@" +
              "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
              "A-Z]{2,7}$";
      Pattern pattern = Pattern.compile(emailRegex);
      if (pattern.matcher(email).matches()) {
         editTextEmail.setError("Email không hợp lệ");
         return false;
      } else return true;
   }

   private Boolean checkPassword(String password, String confirmPass) {
      if (password.length() < 5) {
         editTextPassWord.setError("Mật khẩu quá ngắn, vui lòng nhập thêm");
         return false;
      }
      if (!password.equals(confirmPass)) {
         editTextConfirmPass.setError("Xác nhận mật khẩu không đúng");
         return false;
      } else return true;
   }


}
