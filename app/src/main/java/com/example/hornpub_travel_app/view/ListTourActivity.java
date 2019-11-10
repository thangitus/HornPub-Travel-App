package com.example.hornpub_travel_app.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.adapter.TravelListAdapter;
import com.example.hornpub_travel_app.model.TravelListData;

import java.util.ArrayList;
import java.util.List;

public class ListTourActivity extends AppCompatActivity {
   Intent intent;
   String token;
   TextView textView;
   private List<TravelListData> mTravelList = new ArrayList<>();
   private RecyclerView recyclerView;
   private TravelListAdapter travelListAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_list_tour);
      intent = getIntent();
      token = intent.getStringExtra("token");
      textView = findViewById(R.id.textView);
      prepareTravelListData();
      recyclerView = findViewById(R.id.myRecyclerView);
      travelListAdapter = new TravelListAdapter(this, mTravelList);

      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
      recyclerView.setLayoutManager(mLayoutManager);
      recyclerView.setItemAnimator(new DefaultItemAnimator());
      recyclerView.setAdapter(travelListAdapter);
   }

   private void prepareTravelListData() {
      TravelListData travelListData = new TravelListData("demo", "TP. Hồ Chí Minh - Vũng Tàu", "20/07/2020 - 25/07/2020", "2 người lớn", "3.000.000 VNĐ");
      mTravelList.add(travelListData);

      travelListData = new TravelListData("demo", "TP. Hồ Chí Minh - Vũng Tàu", "20/07/2020 - 25/07/2020", "2 người lớn", "3.000.000 VNĐ");
      mTravelList.add(travelListData);

      travelListData = new TravelListData("demo", "TP. Hồ Chí Minh - Vũng Tàu", "20/07/2020 - 25/07/2020", "2 người lớn", "3.000.000 VNĐ");
      mTravelList.add(travelListData);

      travelListData = new TravelListData("demo", "TP. Hồ Chí Minh - Vũng Tàu", "20/07/2020 - 25/07/2020", "2 người lớn", "3.000.000 VNĐ");
      mTravelList.add(travelListData);

      travelListData = new TravelListData("demo", "TP. Hồ Chí Minh - Vũng Tàu", "20/07/2020 - 25/07/2020", "2 người lớn", "3.000.000 VNĐ");
      mTravelList.add(travelListData);

      travelListData = new TravelListData("demo", "TP. Hồ Chí Minh - Vũng Tàu", "20/07/2020 - 25/07/2020", "2 người lớn", "3.000.000 VNĐ");
      mTravelList.add(travelListData);
   }
}

