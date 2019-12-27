package com.ygaps.travelapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTourFragment extends Fragment implements ItemListener {
   private static MyTourFragment mInstance = null;
   private final String TAG = "MyTourFragment";
   SearchView searchView;
   private ListTourResponse listTourResponse;
   private List<Tour> tourList;
   private ListTourRequest listTourRequest;
   private TravelListAdapter travelListAdapter;
   private RecyclerView recyclerView;
   private int index;
   public MyTourFragment() {
      // Required empty public constructor
   }
   public static MyTourFragment getInstance() {
      if (mInstance == null)
         mInstance = new MyTourFragment();
      return mInstance;
   }
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_my_tour, container, false);
   }
   private void sendListTourRequest(final ListTourRequest listTourRequest) {
      APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      mApplication mApp;
      mApp = (mApplication) getActivity().getApplicationContext();
      String token = mApp.getToken();
      Map<String, Number> params = new HashMap<String, Number>();
      params.put("pageIndex", 1);
      params.put("pageSize", 50);
      Call<ListTourResponse> call = apiService.getHistory(token, params);

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
            Log.wtf(TAG, "onFailure get tour");
         }
      });
   }
   private void createRecyclerView() {
      List<Tour> tours = new ArrayList<>();
      for (int i = 0; i < tourList.size(); i++)
         if (!tourList.get(i).getName().equals("") && tourList.get(i).getStatus() != -1)
            tours.add(tourList.get(i));
      tourList = tours;
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

   }
   private void mapping() {
      recyclerView = getView().findViewById(R.id.myTour);
   }

   @Override
   public void onItemClickListener(int pos) {
      index = pos;
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
