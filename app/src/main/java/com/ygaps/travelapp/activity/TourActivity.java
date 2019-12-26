package com.ygaps.travelapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.ViewPagerAdapter;
import com.ygaps.travelapp.fragment.MemberListFragment;
import com.ygaps.travelapp.fragment.TourDetailFragment;
import com.ygaps.travelapp.model.Tour;

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
      adapter.addFrag(TourDetailFragment.newInstance(tour), "Tour Detail");
      adapter.addFrag(MemberListFragment.newInstance(tour), "User list");
      viewPager.setAdapter(adapter);
   }
   public Tour getTour() {
      return tour;
   }
}
