package com.example.hornpub_travel_app.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hornpub_travel_app.R;

public class ForgotPassWordActivity extends AppCompatActivity {
RadioGroup radioGroupViaChoice;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_forgot_pass_word);
      mapping();
      radioGroupViaChoice.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(RadioGroup radioGroup, int i) {
            Toast.makeText(ForgotPassWordActivity.this, "" + i, Toast.LENGTH_SHORT).show();
         }
      });
   }
   private  void mapping(){
      radioGroupViaChoice=findViewById(R.id.radioGroupVia);
   }
}
