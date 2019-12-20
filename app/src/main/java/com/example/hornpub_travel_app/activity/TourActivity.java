package com.example.hornpub_travel_app.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.adapter.ViewPagerAdapter;
import com.example.hornpub_travel_app.fragment.TourDetailDialogFragment;
import com.example.hornpub_travel_app.model.Tour;
import com.google.android.material.tabs.TabLayout;

public class TourActivity extends AppCompatActivity {
   TabLayout tabLayout;
   ViewPager viewPager;
   Toolbar toolbar;
   Tour tour;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_tour_detail);
      mapping();
      Intent intent = getIntent();
      tour = (Tour) intent.getSerializableExtra("Tour");
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      setupViewPager(viewPager);
      tabLayout.setupWithViewPager(viewPager);
   }
   private void mapping() {
      toolbar = findViewById(R.id.toolbar);
      tabLayout = findViewById(R.id.tabs);
      viewPager = findViewById(R.id.viewpager);
   }
   private void setupViewPager(ViewPager viewPager) {
      ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
      adapter.addFrag(TourDetailDialogFragment.newInstance(tour), "Tour Detail");
      viewPager.setAdapter(adapter);
   }
   public Tour getTour() {
      return tour;
   }
}
