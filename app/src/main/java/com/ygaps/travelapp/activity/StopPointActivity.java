package com.ygaps.travelapp.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.ViewPagerAdapter;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.fragment.ReviewListFragment;
import com.ygaps.travelapp.fragment.StopPointInfoFragment;
import com.ygaps.travelapp.model.Comment;
import com.ygaps.travelapp.model.feedback_stop_point.FeedBackStopPoint;
import com.ygaps.travelapp.model.create_tour.StopPoint;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StopPointActivity extends AppCompatActivity {
   public static String TAG = "StopPointActivity";
   TabLayout tabLayout;
   ViewPager viewPager;
   Toolbar toolbar;
   StopPoint stopPoint;
   List<Comment> comments;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_stop_point);
      mapping();
      Intent intent = getIntent();
      stopPoint = (StopPoint) intent.getSerializableExtra("StopPoint");
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getReview(stopPoint);
      tabLayout.setupWithViewPager(viewPager);
   }
   private void getReview(StopPoint stopPoint) {
      APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      mApplication mApp = (mApplication) getApplicationContext();
      Call<FeedBackStopPoint> call = apiService.getFeedbackStopPoint(mApp.getToken(), stopPoint.getId(), 1, "100");
      call.enqueue(new Callback<FeedBackStopPoint>() {
         @Override
         public void onResponse(Call<FeedBackStopPoint> call, Response<FeedBackStopPoint> response) {
            if (response.code() == 200) {
               comments = new ArrayList<>(response.body().getComments());
               setupViewPager(viewPager);
            }
         }
         @Override
         public void onFailure(Call<FeedBackStopPoint> call, Throwable t) {

         }
      });
   }
   private void mapping() {
      toolbar = findViewById(R.id.toolbar);
      tabLayout = findViewById(R.id.tabs);
      viewPager = findViewById(R.id.viewpager);
   }

   private void setupViewPager(ViewPager viewPager) {
      ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
      adapter.addFrag(StopPointInfoFragment.newInstance(stopPoint), "Stop point info");
      adapter.addFrag(ReviewListFragment.newInstance(false,comments, stopPoint.getId()), "Review");
      viewPager.setAdapter(adapter);
   }
}
