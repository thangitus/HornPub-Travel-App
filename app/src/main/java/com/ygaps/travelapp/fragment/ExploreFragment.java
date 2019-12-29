package com.ygaps.travelapp.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.model.create_tour.StopPoint;
import com.ygaps.travelapp.model.google_map.Coordinate;
import com.ygaps.travelapp.model.google_map.CoordinateSet;
import com.ygaps.travelapp.model.google_map.GetSuggestDestinationsRequest;
import com.ygaps.travelapp.model.google_map.MarkerClusterRenderer;
import com.ygaps.travelapp.model.google_map.MyItem;
import com.ygaps.travelapp.model.google_map.SuggestedDestination;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragment extends Fragment implements OnMapReadyCallback {
   private static final String TAG = "ExploreFragment";
   private static View view;
   private GoogleMap mGoogleMap;
   private MapView mapView;
   private SearchView searchView;
   private Circle firstCircle, secondCircle;
   private ImageButton buttonMyLocation;
   private SuggestedDestination suggestedDestination;
   private ClusterManager<MyItem> mClusterManager;
   public ExploreFragment() {
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
      mGoogleMap.setMyLocationEnabled(false);
      mClusterManager = new ClusterManager<MyItem>(getContext(), mGoogleMap);
      mClusterManager.setRenderer(new MarkerClusterRenderer(getContext(), mGoogleMap, mClusterManager));
      mGoogleMap.setOnCameraIdleListener(mClusterManager);
      mGoogleMap.setOnMarkerClickListener(mClusterManager);

      if (checkPermission()) {
         moveCameraToCurrentLocation(14);
         LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
         @SuppressLint("MissingPermission")
         Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
         LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
         setCircle(latLng);
      }
      mGoogleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
         @Override
         public void onMyLocationChange(Location location) {
            setCircle(new LatLng(location.getLatitude(), location.getLongitude()));
         }
      });
   }
   private int checkSelfPermission(String accessFineLocation) {
      return 1;
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapping();
      setupView();
      getSuggest();
      buttonMyLocation.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            moveCameraToCurrentLocation(15);
         }
      });
   }

   private void setupView() {
      if (mapView != null) {
         mapView.onCreate(null);
         mapView.onResume();
         mapView.getMapAsync(this);
      }
   }

   private void mapping() {
      searchView = getView().findViewById(R.id.searchView);
      mapView = view.findViewById(R.id.mapView);
      buttonMyLocation = view.findViewById(R.id.buttonMyLocation);

   }

   @RequiresApi(api = Build.VERSION_CODES.M)
   private void moveCameraToCurrentLocation(int zoom) {
      LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
      @SuppressLint("MissingPermission")
      Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

      LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
      mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
   }

   private void setCircle(LatLng latLng) {
      if (firstCircle != null) firstCircle.remove();
      firstCircle = mGoogleMap.addCircle(new CircleOptions()
              .center(latLng)
              .radius(10)
              .strokeWidth(0)
              .fillColor(getResources().getColor(R.color.colorBlue)));
      if (secondCircle != null) secondCircle.remove();
      secondCircle = mGoogleMap.addCircle(new CircleOptions()
              .center(latLng)
              .radius(100)
              .strokeWidth(0)
              .fillColor(getResources().getColor(R.color.colorBlueLightTransparency)));
   }

   private void getSuggest() {
      mApplication mApp = (mApplication) getContext().getApplicationContext();
      List<Coordinate> coordList = new ArrayList<>();
      coordList.add(new Coordinate(23.352363, 105.263432));
      coordList.add(new Coordinate(8.597456, 104.916126));
      coordList.add(new Coordinate(13.753587, 106.626427));
      coordList.add(new Coordinate(14.109914, 109.288101));
      List<CoordinateSet> coordinateSets = new ArrayList<>();
      coordinateSets.add(new CoordinateSet(coordList));
      final GetSuggestDestinationsRequest request = new GetSuggestDestinationsRequest(false, coordinateSets);
      APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      Call<SuggestedDestination> call = apiService.getSuggestedDestination(mApp.getToken(), request);
      call.enqueue(new Callback<SuggestedDestination>() {
         @Override
         public void onResponse(Call<SuggestedDestination> call, Response<SuggestedDestination> response) {
            if (response.code() == 200) {
               suggestedDestination = new SuggestedDestination(response.body());
               drawMarker();
            }
         }
         @Override
         public void onFailure(Call<SuggestedDestination> call, Throwable t) {
         }
      });
   }
   private void drawMarker() {
      List<StopPoint> stopPoints = suggestedDestination.getStopPoints();
      for (int i = 0; i < stopPoints.size(); i++) {
         StopPoint stopPoint = stopPoints.get(i);
         addMarker(new LatLng(stopPoint.getLat(), stopPoint.getLongitude()), stopPoint.getName(), stopPoint.getServiceTypeId());
      }
   }

   private void addMarker(LatLng latLng, String title, int serviceTypeId) {
      MyItem offsetItem = new MyItem(latLng.latitude, latLng.longitude, serviceTypeId);
      mClusterManager.addItem(offsetItem);
   }
   private Boolean checkPermission() {
      if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
         ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
         return false;
      } else return true;
   }
}
