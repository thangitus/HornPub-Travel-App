package com.ygaps.travelapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.ygaps.travelapp.fragment.MapViewFragment;
import com.ygaps.travelapp.fragment.ListTourFragment;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.fragment.MyTourFragment;
import com.ygaps.travelapp.fragment.NotificationsFragment;
import com.ygaps.travelapp.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
   BottomNavigationView bottomNavigationView;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_home);
      mapping();

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
