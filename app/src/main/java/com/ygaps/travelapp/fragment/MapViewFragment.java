package com.ygaps.travelapp.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ygaps.travelapp.R;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class MapViewFragment extends Fragment implements OnMapReadyCallback {

   private static View view;
   GoogleMap mGoogleMap;
   MapView mapView;
   SearchView searchView;
   Boolean mLocationPermissionGranted;
   List<Marker> markerList;
   Marker currentMarker = null;

   public MapViewFragment() {
      // Required empty public constructor
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.fragment_explore, container, false);
      return view;
   }

   @Override
   public void onMapReady(GoogleMap googleMap) {
      MapsInitializer.initialize(getContext());
      LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
      if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
         // TODO: Consider calling
         //    Activity#requestPermissions
         // here to request the missing permissions, and then overriding
         //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
         //                                          int[] grantResults)
         // to handle the case where the user grants the permission. See the documentation
         // for Activity#requestPermissions for more details.
         return;
      }
      Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      mGoogleMap = googleMap;
      mGoogleMap.setMyLocationEnabled(false);
      mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 14));
      mGoogleMap.setOnMapLongClickListener(googleMapLongClickListener());
      mGoogleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
         @Override
         public void onMyLocationChange(Location location) {
            if (currentMarker != null) {
               currentMarker.remove();
               currentMarker = null;
            } else
               currentMarker = mGoogleMap.addMarker(new MarkerOptions()
                       .position(new LatLng(location.getLatitude(), location.getLongitude())));
         }
      });

   }
   private int checkSelfPermission(String accessFineLocation) {
      return  1;
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapping();
      setupView();
      markerList = new ArrayList<>();
   }

   private void setupView() {
      if (mapView != null) {
         mapView.onCreate(null);
         mapView.onResume();
         mapView.getMapAsync(this);
      }
   }

   private GoogleMap.OnMapLongClickListener googleMapLongClickListener() {
      GoogleMap.OnMapLongClickListener listener = new GoogleMap.OnMapLongClickListener() {
         @Override
         public void onMapLongClick(LatLng latLng) {
            getAddress(latLng);

         }
      };
      return listener;
   }

   private List<String> getAddress(LatLng latLng) {
      List<String> res = new ArrayList<>();
      final List<Address> addresses;
      Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
      try {
         addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
//         Log.wtf("map", "Address: " + addresses.get(0).getAdminArea());
      } catch (IOException e) {
         e.printStackTrace();
      }
      return res;
   }

   private void mapping() {
      searchView = getView().findViewById(R.id.searchView);
      mapView = view.findViewById(R.id.mapView);
   }

}
