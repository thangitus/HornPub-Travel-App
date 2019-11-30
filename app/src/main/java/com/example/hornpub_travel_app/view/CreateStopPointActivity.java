package com.example.hornpub_travel_app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.application.mApplication;
import com.example.hornpub_travel_app.fragment.StopPointDialogFragment;
import com.example.hornpub_travel_app.model.create_tour.CreateTourRequest;
import com.example.hornpub_travel_app.model.create_tour.StopPoint;
import com.example.hornpub_travel_app.network.APIService;
import com.example.hornpub_travel_app.network.NetworkProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateStopPointActivity extends AppCompatActivity implements OnMapReadyCallback, DialogInterface.OnDismissListener {
   private static final String TAG = "CreateStopPointActivity";
   SearchView searchView;
   MapView mapView;
   GoogleMap mGoogleMap;
   Marker currentMarker = null;
   StopPoint stopPoint;
   CreateTourRequest createTourRequest;
   List<StopPoint> stopPointList;
   mApplication mApp;
   String tourId;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_create_stop_point);
      mapping();
      setupView();
      mApp = (mApplication) getApplicationContext();
      stopPointList = new ArrayList<>();
      Intent intent = getIntent();
      createTourRequest = intent.getParcelableExtra("createTourRequest");
   }
   private void mapping() {
      searchView = findViewById(R.id.searchViewStopPoint);
      mapView = findViewById(R.id.mapViewAddStopPoint);
   }

   public void displayStopPointDialog(StopPoint stopPoint, String province) {
      DialogFragment dialogFragment = StopPointDialogFragment.newInstance(stopPoint, province);
      dialogFragment.show(getSupportFragmentManager(), "dialog");
   }

   @Override
   public void onMapReady(GoogleMap googleMap) {
      MapsInitializer.initialize(this);
      LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
      @SuppressLint("MissingPermission")
      Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      mGoogleMap = googleMap;
      mGoogleMap.setMyLocationEnabled(false);
      mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 14));
      mGoogleMap.setOnMapClickListener(googleMapClickListener());
      mGoogleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
         @Override
         public void onMyLocationChange(Location location) {
            if (currentMarker != null) {
               currentMarker.remove();
               currentMarker = null;
            } else
               currentMarker = mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
         }
      });
   }
   @Override
   public void onPointerCaptureChanged(boolean hasCapture) {

   }
   private GoogleMap.OnMapClickListener googleMapClickListener() {
      GoogleMap.OnMapClickListener listener = new GoogleMap.OnMapClickListener() {
         @Override
         public void onMapClick(LatLng latLng) {
            getAddress(latLng);
         }
      };
      return listener;
   }

   private List<String> getAddress(LatLng latLng) {
      List<String> res = new ArrayList<>();
      stopPoint = new StopPoint();
      final List<Address> addresses;
      Geocoder geocoder = new Geocoder(this, Locale.getDefault());
      try {
         addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
         String address = addresses.get(0).getAddressLine(0);
         if (address != null) {
            stopPoint.setAddress(address.replace("Address: ", ""));
            stopPoint.setAddress(address.replace("Unnamed Road, ", ""));
         }
         stopPoint.setLat((float) latLng.latitude);
         stopPoint.setLongitude((float) latLng.longitude);
         displayStopPointDialog(stopPoint, addresses.get(0).getAdminArea());
      } catch (IOException e) {
         e.printStackTrace();
      }
      return res;
   }
   private void setupView() {
      if (mapView != null) {
         mapView.onCreate(null);
         mapView.onResume();
         mapView.getMapAsync(this);
      }
   }

   @Override
   public void onDismiss(DialogInterface dialogInterface) {
      Log.wtf(TAG, "on Dismiss");
      stopPoint.setTourId(tourId);
      stopPointList.add(stopPoint);
      if (stopPointList.size() == 2) {
         StopPoint sourceStopPoint, desStopPoint;
         sourceStopPoint = stopPointList.get(0);
         desStopPoint = stopPointList.get(1);
         createTourRequest.setSourceLat(sourceStopPoint.getLat());
         createTourRequest.setSourceLong(sourceStopPoint.getLongitude());
         createTourRequest.setDesLat(desStopPoint.getLat());
         createTourRequest.setDesLong(desStopPoint.getLongitude());
         createTourRequest.setAvatar(mApp.getAvatarBase64());
         sendCreateTourRequest(createTourRequest);
      }
   }
   private void sendCreateTourRequest(CreateTourRequest createTourRequest) {
      APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      Call<ResponseBody> call = apiService.sendCreateTourRequest(mApp.getToken(), createTourRequest);
      call.enqueue(new Callback<ResponseBody>() {
         @Override
         public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            Log.d(TAG, "On Response");
         }
         @Override
         public void onFailure(Call<ResponseBody> call, Throwable t) {

         }
      });
   }
}
