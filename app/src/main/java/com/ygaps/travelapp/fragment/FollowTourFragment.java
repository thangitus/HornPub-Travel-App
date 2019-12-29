package com.ygaps.travelapp.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.model.CurrentCoordinateRequest;
import com.ygaps.travelapp.model.RecordAudio;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.model.google_map.Coordinate;
import com.ygaps.travelapp.model.google_map.MarkerClusterRenderer;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowTourFragment extends Fragment implements OnMapReadyCallback {
   private static final String TAG = "FollowTourFragment";
   private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
   private static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 2;

   List<Coordinate> coordinates;
   private List<LatLng> latLngList;
   private GoogleMap mGoogleMap;
   private MapView mapView;
   private Boolean isRunning = false;
   private Tour tour;
   private LottieAnimationView buttonRecord;
   private RecordAudio recordAudio;
   private ImageButton buttonWarning;

   @SuppressLint("HandlerLeak")
   private Handler handler = new Handler() {
      @Override
      public void handleMessage(@NonNull Message msg) {
         super.handleMessage(msg);
         Boolean isUpdate = (Boolean) msg.obj;
         if (isUpdate) {
            mGoogleMap.clear();
            for (int i = 0; i < latLngList.size(); i++)
               drawMarker(latLngList.get(i));
         }
      }
   };

   public FollowTourFragment() {
   }

   public static FollowTourFragment newInstance(Tour tour) {
      FollowTourFragment fragment = new FollowTourFragment();
      fragment.setData(tour);
      return fragment;
   }
   private void setData(Tour tour) {
      this.tour = tour;
   }
   @Override
   public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_follow_tour, container, false);
   }
   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapping();
      setupView();
      checkPermission();
      recordAudio = new RecordAudio(getContext(), buttonRecord);
   }

   private void setupView() {
      if (mapView != null) {
         mapView.onCreate(null);
         mapView.onResume();
         mapView.getMapAsync(this);
      }
   }

   private void mapping() {
      mapView = getView().findViewById(R.id.mapViewFollow);
      buttonRecord = getView().findViewById(R.id.buttonRecord);
      buttonWarning = getView().findViewById(R.id.buttonWarning);
      buttonWarning.setVisibility(View.GONE);
   }
   @Override
   public void onMapReady(GoogleMap googleMap) {
      MapsInitializer.initialize(getContext());
      mGoogleMap = googleMap;
      moveCameraToCurrentLocation(14);
      mGoogleMap.setMyLocationEnabled(false);
      sendLocation();
   }
   private void sendLocation() {
      final APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      final mApplication mApp = (mApplication) getContext().getApplicationContext();
      new Thread(new Runnable() {
         @Override
         public void run() {
            while (isRunning) {
               LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
               @SuppressLint("MissingPermission")
               Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
               LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
               final CurrentCoordinateRequest request = new CurrentCoordinateRequest(tour.getId(), latLng.latitude, latLng.longitude);
               Call<List<Coordinate>> call = apiService.getCoordinateMember(mApp.getToken(), request);
               call.enqueue(new Callback<List<Coordinate>>() {
                  @Override
                  public void onResponse(Call<List<Coordinate>> call, Response<List<Coordinate>> response) {
                     latLngList = new ArrayList<>();
                     coordinates = new ArrayList<>(response.body());
                     for (int i = 0; i < coordinates.size(); i++) {
                        Coordinate coordinate = coordinates.get(i);
                        latLngList.add(new LatLng(coordinate.getLat(), coordinate.getLongtitude()));
                     }
                     Message message = handler.obtainMessage(1, true);
                     handler.sendMessage(message);
                  }
                  @Override
                  public void onFailure(Call<List<Coordinate>> call, Throwable t) {
                     Log.d(TAG, "onFailure");

                  }
               });
               try {
                  Thread.sleep(10000);
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }

         }
      }).start();
   }

   @RequiresApi(api = Build.VERSION_CODES.M)
   private void moveCameraToCurrentLocation(int zoom) {
      LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
      @SuppressLint("MissingPermission")
      Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

      LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
      mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
   }

   private void drawMarker(LatLng latLng) {
      BitmapDescriptor icon = MarkerClusterRenderer.bitmapDescriptorFromVector(getContext(), R.drawable.ic_motocross);
      MarkerOptions markerOptions = new MarkerOptions()
              .position(latLng)
              .icon(icon);
      mGoogleMap.addMarker(markerOptions);
   }

   @Override
   public void onResume() {
      super.onResume();
      isRunning = true;
   }
   @Override
   public void onPause() {
      super.onPause();
      isRunning = false;
   }
   @Override
   public void onStop() {
      super.onStop();
      recordAudio.releaseRecorder();
   }
   private void checkPermission() {
      if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
         ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
      else
         recordAudio = new RecordAudio(getContext(), buttonRecord);

      if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
         ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_ACCESS_FINE_LOCATION);
   }
   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
      if (requestCode == MY_PERMISSIONS_REQUEST_RECORD_AUDIO && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
         recordAudio = new RecordAudio(getContext(), buttonRecord);
      else {
         buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Toast.makeText(getContext(), "No permission", Toast.LENGTH_SHORT).show();
            }
         });
      }
   }
}
