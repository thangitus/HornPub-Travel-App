package com.example.hornpub_travel_app.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.fragment.MapViewFragment;

public class CreateStopPointActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create_stop_point);
      openMapFragment();
   }

   private void openMapFragment() {
      Fragment fragmentMap;
      fragmentMap = new MapViewFragment();
      FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      fragmentTransaction.replace(R.id.frame_map, fragmentMap);
      fragmentTransaction.commit();
   }
}
