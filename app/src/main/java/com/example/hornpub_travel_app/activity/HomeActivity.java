package com.example.hornpub_travel_app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hornpub_travel_app.fragment.MapViewFragment;
import com.example.hornpub_travel_app.fragment.ListTourFragment;
import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.fragment.MyTourFragment;
import com.example.hornpub_travel_app.fragment.NotificationsFragment;
import com.example.hornpub_travel_app.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
   BottomNavigationView bottomNavigationView;
   Intent intent;
   String token;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_home);
      mapping();
      intent = getIntent();
      token = intent.getStringExtra("token");
      bottomNavigationView.setOnNavigationItemSelectedListener(mNavigationListener);
      loadFragment(new ListTourFragment());
   }

   private void mapping() {
      bottomNavigationView = findViewById(R.id.navigation);
   }

   private BottomNavigationView.OnNavigationItemSelectedListener mNavigationListener
           = new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
         Fragment fragment;
         switch (menuItem.getItemId()) {
            case R.id.navigationListTour:
               fragment = new ListTourFragment();
               loadFragment(fragment);
               return true;
            case R.id.navigationMyTour:
               fragment = MyTourFragment.getInstance();
               loadFragment(fragment);
               return true;
            case R.id.navigationCreateTour:
               fragment = new MapViewFragment();
               loadFragment(fragment);
               return true;
            case R.id.navigationNotification:
               fragment = new SettingFragment();
               loadFragment(fragment);
               return true;
            case R.id.navigationSetting:
               fragment = new NotificationsFragment();
               loadFragment(fragment);
               return true;
         }
         return false;
      }
   };

   private void loadFragment(Fragment fragment) {
      FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      fragmentTransaction.replace(R.id.frame_container, fragment);
      fragmentTransaction.commit();
   }
}
