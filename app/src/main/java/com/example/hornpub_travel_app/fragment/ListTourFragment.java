package com.example.hornpub_travel_app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.adapter.TravelListAdapter;
import com.example.hornpub_travel_app.model.ListTourRequest;
import com.example.hornpub_travel_app.model.ListTourResponse;
import com.example.hornpub_travel_app.model.Tour;
import com.example.hornpub_travel_app.network.APIService;
import com.example.hornpub_travel_app.network.NetworkProvider;
import com.example.hornpub_travel_app.view.CreateTourActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListTourFragment extends Fragment {
   String token;
   SearchView searchView;
   ListTourResponse listTourResponse;
   List<Tour> tourList;
   APIService apiService;
   ListTourRequest listTourRequest;
   private RecyclerView recyclerView;
   ImageButton buttonAdd;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_list_tour, container, false);
      return view;
   }

   private void sendListTourRequest(final ListTourRequest listTourRequest) {
      apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);

      Map<String, String> params = new HashMap<String, String>();
      params.put("rowPerPage", "8");
      params.put("pageNum", "1");
      Call<ListTourResponse> call = apiService.getListTour(token, params);

      call.enqueue(new Callback<ListTourResponse>() {
         @Override
         public void onResponse(Call<ListTourResponse> call, Response<ListTourResponse> response) {
            if (response.code() == 200) {
               listTourResponse = new ListTourResponse(response.body());
               tourList = new ArrayList<>();
               tourList = listTourResponse.getTours();
               createRecyclerView();
            } else if (response.code() == 500) {
               Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
            }
         }

         @Override
         public void onFailure(Call<ListTourResponse> call, Throwable t) {
         }
      });
   }

   private void createRecyclerView() {
      TravelListAdapter travelListAdapter;
      travelListAdapter = new TravelListAdapter(getContext(), tourList);
      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
      recyclerView.setLayoutManager(mLayoutManager);
      recyclerView.setItemAnimator(new DefaultItemAnimator());
      recyclerView.setAdapter(travelListAdapter);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapping();
      token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI3MSIsInBob25lIjoiMDE4NzYyMTQzNCIsImVtYWlsIjoibmhhdHJhbmdAZ21haWwuY29tIiwiZXhwIjoxNTc2NDA2NzIwNzQ2LCJhY2NvdW50IjoidXNlciIsImlhdCI6MTU3MzgxNDcyMH0.at7-CUH0I1nwH6Dlq9II4gsvaJ5WGUSKXzNNtvYjG-U";
      sendListTourRequest(listTourRequest);
      final Intent intent = new Intent(getActivity(), CreateTourActivity.class);
      buttonAdd.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(intent);
         }
      });
   }

   private void mapping() {
      recyclerView = getView().findViewById(R.id.myRecyclerView);
      buttonAdd = getView().findViewById(R.id.buttonAdd);

   }
}