package com.example.hornpub_travel_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.model.user.RegisterRequest;
import com.example.hornpub_travel_app.model.user.RegisterResponse;
import com.example.hornpub_travel_app.network.APIService;
import com.example.hornpub_travel_app.network.NetworkProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
   EditText editTextFullName, editTextEmail, editTextPhone, editTextPassWord, editTextConfirmPass;
   Button buttonSignUp;
   RegisterRequest registerRequest;
   RegisterResponse registerResponse;
   APIService apiService;

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

   private void sendRegisterRequest(final RegisterRequest registerRequest) {
      apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      Call<RegisterResponse> call = apiService.register(registerRequest);
      call.enqueue(new Callback<RegisterResponse>() {
         @Override
         public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

            //If Success(200 - OK)
            if (response.code() == 200) {
               registerResponse = new RegisterResponse(response.body());
               finish();
            }
            if (response.code() == 400) {
               try {
                  JSONObject jObjError = new JSONObject(response.errorBody().string());
                  int error = jObjError.getInt("error");
                  if (error == 2) {
                     editTextPhone.setError("Email và số điện thoại đã được sử dụng");
                  } else {
                     JSONObject jsonObjectMessage = jObjError.getJSONArray("message").getJSONObject(0);
                     String param;
                     param = jsonObjectMessage.getString("param");
                     if (param.equals("email"))
                        editTextEmail.setError("Email đăng kí đã được sử dụng");
                     else
                        editTextPhone.setError("Số điện thoại đã được sử dụng");
                  }
               } catch (JSONException e) {
                  e.printStackTrace();
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
            if (response.code() == 503)
               Toast.makeText(SignUpActivity.this, "Lỗi máy chủ khi tạo người dùng", Toast.LENGTH_SHORT).show();
         }

         @Override
         public void onFailure(Call<RegisterResponse> call, Throwable t) {

         }
      });
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

      if (!checkEmail(email)) return false;
      if (!checkPhoneNumber(phone)) return false;
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
      if (!pattern.matcher(email).matches()) {
         editTextEmail.setError("Email không hợp lệ");
         return false;
      } else return true;
   }

   private Boolean checkPassword(String password, String confirmPass) {
      if (password.length() < 5) {
         editTextPassWord.setError("Mật khẩu quá ngắn, vui lòng nhập thêm");
         return false;
      }
      if (confirmPass.length() < 1) {
         editTextConfirmPass.setError("Vui lòng nhập xác nhận mật khẩu");
         return false;
      }
      if (!password.equals(confirmPass)) {
         editTextConfirmPass.setError("Xác nhận mật khẩu không đúng");
         return false;
      } else return true;
   }

   private boolean checkPhoneNumber(String phoneNumber) {
      if (phoneNumber.length() < 1) {
         editTextPhone.setError("Số điện thoại không được rỗng");
         return false;
      }

      String phoneRegex = "^[0]+\\d{9,10}$";
      Pattern pattern = Pattern.compile(phoneRegex);
      if (!pattern.matcher(phoneNumber).matches()) {
         editTextPhone.setError("Số điện thoại không hợp lệ");
         return false;
      } else return true;
   }

}
