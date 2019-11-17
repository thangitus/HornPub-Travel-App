package com.example.hornpub_travel_app.fragment;


import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hornpub_travel_app.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapViewFragment extends Fragment implements OnMapReadyCallback {

   private static View view;
   GoogleMap mGoogleMap;
   MapView mapView;
   Boolean mLocationPermissionGranted;
   Marker currentMarker = null;

   public MapViewFragment() {
      // Required empty public constructor
   }


   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.fragment_map, container, false);
      return view;
   }

   @Override
   public void onMapReady(GoogleMap googleMap) {

      MapsInitializer.initialize(getContext());
      mGoogleMap = googleMap;
      LatLng latLng = new LatLng(-33.867, 151.206);
      mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
      mGoogleMap.setMyLocationEnabled(true);
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
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapView = view.findViewById(R.id.mapView);
      if (mapView != null) {
         mapView.onCreate(null);
         mapView.onResume();
         mapView.getMapAsync(this);
      }
   }
}
