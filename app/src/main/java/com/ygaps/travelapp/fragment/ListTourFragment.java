package com.ygaps.travelapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.activity.CreateTourActivity;
import com.ygaps.travelapp.activity.TourActivity;
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
   TextView textViewSummary;
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
      params.put("rowPerPage", "120");
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
      List<Tour> tours = new ArrayList<>();
      for (int i = 0; i < tourList.size(); i++) {
         if (tourList.get(i).getName() == null) continue;
         if (!tourList.get(i).getName().equals("") && tourList.get(i).getStatus() != -1)
            tours.add(tourList.get(i));
      }
      tourList = tours;
      if (tourList.size() == 0) {
         sendListTourRequest(listTourRequest);
         return;
      }
      textViewSummary.setText(tourList.size() + " trips");
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
      textViewSummary = getView().findViewById(R.id.textViewSummary);
   }

   private void searchTour(String string) {
      tourList.clear();
      for (int i = 0; i < tourListSearch.size(); i++) {
         if (tourListSearch.get(i).getName().contains(string))
            tourList.add(tourListSearch.get(i));
      }
      travelListAdapter.notifyDataSetChanged();
   }
   @Override
   public void onItemClickListener(int pos) {
      final int index = pos;
      APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      mApplication mApp;
      mApp = (mApplication) getActivity().getApplicationContext();
      String token = mApp.getToken();
      Call<Tour> call = apiService.getTourInfo(token, tourList.get(index).getId());
      call.enqueue(new Callback<Tour>() {
         @Override
         public void onResponse(Call<Tour> call, Response<Tour> response) {
            if (response.code() == 200) {
               Tour tour = new Tour(response.body());
               tourList.set(index, tour);
               Intent intent = new Intent(getActivity(), TourActivity.class);
               Bundle bundle = new Bundle();
               bundle.putSerializable("Tour", tourList.get(index));
               intent.putExtras(bundle);
               startActivity(intent);
            }

         }
         @Override
         public void onFailure(Call<Tour> call, Throwable t) {

         }
      });

   }
}