package com.example.hornpub_travel_app.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.adapter.TravelListAdapter;
import com.example.hornpub_travel_app.model.ListTourRequest;
import com.example.hornpub_travel_app.model.ListTourResponse;
import com.example.hornpub_travel_app.model.Tour;
import com.example.hornpub_travel_app.model.TravelListData;
import com.example.hornpub_travel_app.network.APIService;
import com.example.hornpub_travel_app.network.NetworkProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTourActivity extends AppCompatActivity {
   final static String TAG = "ListTourActivity";
   Intent intent;
   String token;
   ListTourResponse listTourResponse;
   List<Tour> tourList;
   APIService apiService;
   ListTourRequest listTourRequest;
   private List<TravelListData> mTravelList = new ArrayList<>();
   private RecyclerView recyclerView;
   private TravelListAdapter travelListAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_list_tour);
      intent = getIntent();
      token = intent.getStringExtra("token");
      listTourRequest = new ListTourRequest(1, 7, null, null);
      sendListTourRequest(listTourRequest);
      createRecyclerView();
   }

   private void sendListTourRequest(final ListTourRequest listTourRequest) {
      apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);

      Map<String, String> params = new HashMap<String, String>();
      params.put("rowPerPage", "8");
      params.put("pageNum", "1");
      Call<ListTourResponse> call = apiService.getListTour(token, params);
//      Call<ListTourResponse> call = apiService.getListTour(token, listTourRequest);
      call.enqueue(new Callback<ListTourResponse>() {
         @Override
         public void onResponse(Call<ListTourResponse> call, Response<ListTourResponse> response) {
            if (response.code() == 200) {
               listTourResponse = new ListTourResponse(response.body());
               tourList = new ArrayList<>();
               tourList = listTourResponse.getTours();
            } else if (response.code() == 500) {
               Toast.makeText(ListTourActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }
         }

         @Override
         public void onFailure(Call<ListTourResponse> call, Throwable t) {
            Log.d(TAG, "onFailure");
         }
      });

   }

   private void createRecyclerView() {
      recyclerView = findViewById(R.id.myRecyclerView);
      travelListAdapter = new TravelListAdapter(this, tourList);
      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
      recyclerView.setLayoutManager(mLayoutManager);
      recyclerView.setItemAnimator(new DefaultItemAnimator());
      recyclerView.setAdapter(travelListAdapter);
   }
}

