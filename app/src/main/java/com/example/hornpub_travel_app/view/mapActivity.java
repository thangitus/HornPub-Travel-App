package com.example.hornpub_travel_app.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hornpub_travel_app.R;

public class mapActivity extends AppCompatActivity {
    SearchView searchLocation;

    @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_map);

      searchLocation = findViewById(R.id.search);
    }


}
