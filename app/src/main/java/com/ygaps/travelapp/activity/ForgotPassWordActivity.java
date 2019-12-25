package com.ygaps.travelapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ygaps.travelapp.R;

public class ForgotPassWordActivity extends AppCompatActivity {
   LinearLayout viaSMS, viaEmail;
   EditText editTextInput;
   ImageView imgSMS, imgEmail;
   TextView tvSMS, tvEmail;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_forgot_pass_word);
      mapping();

      viaEmail.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            choiceEmail();
         }
      });
      viaSMS.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            choiceSMS();
         }
      });
      
   }

   private void mapping() {
      viaSMS = findViewById(R.id.viaSMS);
      viaEmail = findViewById(R.id.viaEmail);
      editTextInput = findViewById(R.id.editTextVia);
      imgSMS = findViewById(R.id.imgViaSMS);
      imgEmail = findViewById(R.id.imgViaEmail);
      tvEmail = findViewById(R.id.textViewViaEmail);
      tvSMS = findViewById(R.id.textViewViaSms);
   }

   private void choiceSMS() {
      imgSMS.setImageResource(R.drawable.icons8_sms_blue_96);
      tvSMS.setTextColor(getResources().getColor(R.color.colorBlue));
      imgEmail.setImageResource(R.drawable.icons8_mail_gray_96);
      tvEmail.setTextColor(getResources().getColor(R.color.colorGray));
      editTextInput.setHint(getResources().getString(R.string.enterPhone));
   }

   private void choiceEmail() {
      imgEmail.setImageResource(R.drawable.icons8_mail_blue_96);
      tvEmail.setTextColor(getResources().getColor(R.color.colorBlue));
      imgSMS.setImageResource(R.drawable.icons8_sms_gray_96);
      tvSMS.setTextColor(getResources().getColor(R.color.colorGray));
      editTextInput.setHint(getResources().getString(R.string.enterEmail));
   }
}
