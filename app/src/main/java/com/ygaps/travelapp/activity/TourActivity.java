package com.ygaps.travelapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.ViewPagerAdapter;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.fragment.FollowTourFragment;
import com.ygaps.travelapp.fragment.MemberListFragment;
import com.ygaps.travelapp.fragment.ReviewListFragment;
import com.ygaps.travelapp.fragment.TourDetailFragment;
import com.ygaps.travelapp.model.Comment;
import com.ygaps.travelapp.model.GetReviewResponse;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourActivity extends AppCompatActivity {
   public static String TAG = "TourActivity";
   TabLayout tabLayout;
   ViewPager viewPager;
   Toolbar toolbar;
   Tour tour;
   List<Comment> comments;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_tour_detail);
      mapping();
      Intent intent = getIntent();
      tour = (Tour) intent.getSerializableExtra("Tour");
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getReview(tour);
      tabLayout.setupWithViewPager(viewPager);
      viewPager.setOffscreenPageLimit(0);
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
      adapter.addFrag(ReviewListFragment.newInstance(comments, tour.getId()), "Review");
      adapter.addFrag(FollowTourFragment.newInstance(tour), "Follow tour");

      viewPager.setAdapter(adapter);
   }
   private void getReview(Tour tour) {
      mApplication mApp;
      mApp = (mApplication) getApplicationContext();
      String token = mApp.getToken();
      comments = new ArrayList<>();
      APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      Call<GetReviewResponse> call = apiService.getReview(token, tour.getId(), 1, "20");
      call.enqueue(new Callback<GetReviewResponse>() {
         @Override
         public void onResponse(Call<GetReviewResponse> call, Response<GetReviewResponse> response) {
            if (response.code() == 200) {
               GetReviewResponse getReviewResponse = new GetReviewResponse(response.body());
               comments = getReviewResponse.getReviewList();
               setupViewPager(viewPager);
               Log.i(TAG, "Get review success");
            }
         }
         @Override
         public void onFailure(Call<GetReviewResponse> call, Throwable t) {
            Log.i(TAG, "Get review fail");
            setupViewPager(viewPager);
         }
      });
   }
   public Tour getTour() {
      return tour;
   }
}
