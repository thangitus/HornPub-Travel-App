package com.ygaps.travelapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.activity.CreateTourActivity;
import com.ygaps.travelapp.adapter.ItemListener;
import com.ygaps.travelapp.adapter.TravelListAdapter;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.model.ListTourRequest;
import com.ygaps.travelapp.model.ListTourResponse;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTourFragment extends Fragment implements ItemListener {
   public static String TAG = "ListTourFragment";
   String token;
   SearchView searchView;
   ListTourResponse listTourResponse;
   List<Tour> tourList, tourListSearch;
   APIService apiService;
   ListTourRequest listTourRequest;
   ImageButton buttonAdd;
   TravelListAdapter travelListAdapter;
   private RecyclerView recyclerView;
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_list_tour, container, false);
      return view;
   }

   private void sendListTourRequest(final ListTourRequest listTourRequest) {
      apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      mApplication mApp;
      mApp = (mApplication) getActivity().getApplicationContext();
      token = mApp.getToken();
      Map<String, String> params = new HashMap<String, String>();
      params.put("rowPerPage", "40");
      params.put("pageNum", String.valueOf((int) (Math.random() * 50)));
      Call<ListTourResponse> call = apiService.getListTour(token, params);

      call.enqueue(new Callback<ListTourResponse>() {
         @Override
         public void onResponse(Call<ListTourResponse> call, Response<ListTourResponse> response) {
            if (response.code() == 200) {
               listTourResponse = new ListTourResponse(response.body());
               tourList = new ArrayList<>();
               tourList = listTourResponse.getTours();
               tourListSearch = new ArrayList<>(tourList);
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
      travelListAdapter = new TravelListAdapter(getContext(), tourList, this);
      RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
      recyclerView.setLayoutManager(mLayoutManager);
      recyclerView.setItemAnimator(new DefaultItemAnimator());
      recyclerView.setAdapter(travelListAdapter);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapping();
      sendListTourRequest(listTourRequest);
      final Intent intent = new Intent(getActivity(), CreateTourActivity.class);
      buttonAdd.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            startActivity(intent);
         }
      });
      searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
         @Override
         public boolean onQueryTextSubmit(String s) {
            return false;
         }
         @Override
         public boolean onQueryTextChange(String s) {
            searchTour(s);
            return false;
         }
      });
      searchView.setOnCloseListener(new SearchView.OnCloseListener() {
         @Override
         public boolean onClose() {
            tourList = new ArrayList<>(tourListSearch);
            travelListAdapter.notifyDataSetChanged();
            return false;
         }
      });
   }

   private void mapping() {
      recyclerView = getView().findViewById(R.id.myRecyclerView);
      buttonAdd = getView().findViewById(R.id.buttonAdd);
      searchView = getView().findViewById(R.id.searchView);
   }
   @Override
   public void onItemClickListener(int pos) {

   }
   private void searchTour(String string) {
      tourList.clear();
      for (int i = 0; i < tourListSearch.size(); i++) {
         if (tourListSearch.get(i).getName().contains(string))
            tourList.add(tourListSearch.get(i));
      }
      travelListAdapter.notifyDataSetChanged();
   }
}