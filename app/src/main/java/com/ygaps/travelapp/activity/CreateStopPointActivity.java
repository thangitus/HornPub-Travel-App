package com.ygaps.travelapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.fragment.StopPointDialog.AddStopPointDialogFragment;
import com.ygaps.travelapp.model.create_tour.AddStopPointRequest;
import com.ygaps.travelapp.model.create_tour.CreateTourRequest;
import com.ygaps.travelapp.model.create_tour.StopPoint;
import com.ygaps.travelapp.model.google_map.Coordinate;
import com.ygaps.travelapp.model.google_map.CoordinateSet;
import com.ygaps.travelapp.model.google_map.GetSuggestDestinationsRequest;
import com.ygaps.travelapp.model.google_map.MarkerClusterRenderer;
import com.ygaps.travelapp.model.google_map.MyItem;
import com.ygaps.travelapp.model.google_map.SuggestedDestination;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
   StopPoint stopPoint;
   ImageButton buttonMyLocation;
   CreateTourRequest createTourRequest;
   mApplication mApp;
   Circle firstCircle, secondCircle;
   List<Marker> markerList;
   PolylineOptions polylineOptions;
   AddStopPointRequest addStopPointRequest;
   SuggestedDestination suggestedDestination;
   private ClusterManager<MyItem> mClusterManager;

   @SuppressLint("HandlerLeak")
   private Handler handler = new Handler() {
      @Override
      public void handleMessage(@NonNull Message msg) {
         super.handleMessage(msg);
         String data = (String) msg.obj;
         if (data.equals("directional")) {
            mGoogleMap.addPolyline(polylineOptions).setTag("Directional");
         }
      }
   };
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      addStopPointRequest = new AddStopPointRequest();
      setContentView(R.layout.activity_create_stop_point);
      mapping();
      setupView();
      getSuggest();
      mApp = (mApplication) getApplicationContext();
      Intent intent = getIntent();
      createTourRequest = new CreateTourRequest();
      createTourRequest = intent.getParcelableExtra("createTourRequest");
      buttonMyLocation.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            moveCameraToCurrentLocation(15);
         }
      });
   }
   private void mapping() {
      searchView = findViewById(R.id.searchViewStopPoint);
      mapView = findViewById(R.id.mapViewAddStopPoint);
      buttonMyLocation = findViewById(R.id.buttonMyLocation);
   }

   public void displayStopPointDialog(AddStopPointRequest addStopPointRequest, StopPoint stopPoint, String province) {
      DialogFragment dialogFragment = AddStopPointDialogFragment.newInstance(addStopPointRequest, stopPoint, province);
      dialogFragment.show(getSupportFragmentManager(), "dialog");
   }

   @RequiresApi(api = Build.VERSION_CODES.M)
   @Override
   public void onMapReady(GoogleMap googleMap) {
      markerList = new ArrayList<>();
      MapsInitializer.initialize(this);
      mGoogleMap = googleMap;
      moveCameraToCurrentLocation(14);
      mGoogleMap.setMyLocationEnabled(false);
      mGoogleMap.setOnMapClickListener(googleMapClickListener());
      mClusterManager = new ClusterManager<MyItem>(this, mGoogleMap);
      mClusterManager.setRenderer(new MarkerClusterRenderer(this, mGoogleMap, mClusterManager));
      mGoogleMap.setOnCameraIdleListener(mClusterManager);
      mGoogleMap.setOnMarkerClickListener(mClusterManager);

      LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
      if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
         return;
      }
      Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
      LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
      setCircle(latLng);
      mGoogleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
         @Override
         public void onMyLocationChange(Location location) {
            setCircle(new LatLng(location.getLatitude(), location.getLongitude()));
         }
      });
   }

   @RequiresApi(api = Build.VERSION_CODES.M)
   private void moveCameraToCurrentLocation(int zoom) {
      LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

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
   private OnMapClickListener googleMapClickListener() {
      OnMapClickListener listener = new OnMapClickListener() {
         @Override
         public void onMapClick(LatLng latLng) {
            Address address = getAddress(latLng);
            String addressStr = address.getAddressLine(0);
            if (address != null) {
               stopPoint.setAddress(addressStr.replace("Address: ", ""));
               stopPoint.setAddress(addressStr.replace("Unnamed Road, ", ""));
            }
            stopPoint.setLat((float) latLng.latitude);
            stopPoint.setLongitude((float) latLng.longitude);
            displayStopPointDialog(addStopPointRequest, stopPoint, address.getAdminArea());
         }
      };
      return listener;
   }
   private void addMarker(LatLng latLng, String title) {
      BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.placeholder);
      Log.wtf(TAG, title);
      MarkerOptions markerOptions = new MarkerOptions()
              .position(latLng)
              .icon(icon)
              .title(title);
      markerList.add(mGoogleMap.addMarker(markerOptions));
      markerList.get(markerList.size() - 1).showInfoWindow();
   }
   private Address getAddress(LatLng latLng) {
      stopPoint = new StopPoint();
      final List<Address> addresses;
      Geocoder geocoder = new Geocoder(this, Locale.getDefault());
      try {
         addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
         return addresses.get(0);

      } catch (IOException e) {
         e.printStackTrace();
      }
      return null;
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
      String title = "";
      if (addStopPointRequest.getStopPoints().size() == 1) title = "Điểm bắt đầu";
      else if (addStopPointRequest.getStopPoints().size() == 2) title = "Điểm kết thúc";

      addMarker(new LatLng(stopPoint.getLat(), stopPoint.getLongitude()), title);
      if (addStopPointRequest.getStopPoints().size() == 2) {
         StopPoint sourceStopPoint, desStopPoint;
         sourceStopPoint = addStopPointRequest.getStopPoints().get(0);
         desStopPoint = addStopPointRequest.getStopPoints().get(1);
         createTourRequest.setSourceLat(sourceStopPoint.getLat());
         createTourRequest.setSourceLong(sourceStopPoint.getLongitude());
         createTourRequest.setDesLat(desStopPoint.getLat());
         createTourRequest.setDesLong(desStopPoint.getLongitude());
//         createTourRequest.setAvatar(mApp.getAvatarBase64());
         sendCreateTourRequest(createTourRequest);
      }

      if (addStopPointRequest.getStopPoints().size() >= 2) {
         final String request = makeURL(addStopPointRequest.getStopPoints());
         new Thread(new Runnable() {
            @Override
            public void run() {
               try {
                  String data = downloadUrl(request);
                  JSONObject jsonObject = new JSONObject(data);
                  jsonObject = (JSONObject) jsonObject.getJSONArray("routes").get(0);
                  JSONArray jLegs = jsonObject.getJSONArray("legs");
                  polylineOptions = new PolylineOptions();

                  for (int k = 0; k < jLegs.length(); k++) {
                     JSONObject step, start, leg;
                     JSONArray steps;
                     leg = (JSONObject) jLegs.get(k);
                     steps = leg.getJSONArray("steps");
                     start = (JSONObject) steps.get(0);
                     start = start.getJSONObject("start_location");
                     LatLng start_location;
                     start_location = new LatLng(start.getDouble("lat"), start.getDouble("lng"));
                     polylineOptions.add(start_location);
                     for (int i = 1; i < steps.length(); i++) {
                        JSONObject polyline;
                        step = (JSONObject) steps.get(i);
                        polyline = step.getJSONObject("polyline");
                        List<LatLng> latLngs = decodePoly(polyline.getString("points"));
                        for (int j = 0; j < latLngs.size(); j++) {
                           polylineOptions.add(latLngs.get(j));
                        }
                     }
                  }
                  Message message = handler.obtainMessage(1, "directional");
                  handler.sendMessage(message);
               } catch (IOException | JSONException e) {
                  e.printStackTrace();
               }

            }
         }).start();
      }
   }

   private void sendCreateTourRequest(CreateTourRequest createTourRequest) {
      APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      Call<ResponseBody> call = apiService.sendCreateTourRequest(mApp.getToken(), createTourRequest);
      call.enqueue(new Callback<ResponseBody>() {
         @Override
         public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.code() == 200) {
               try {
                  String stringResponse = response.body().string();
                  JSONObject jResponse = new JSONObject(stringResponse);
                  addStopPointRequest.setTourId(jResponse.getString("id"));
               } catch (IOException | JSONException e) {
                  e.printStackTrace();
               }
            }
            Log.i(TAG, "On Response Create Tour");
         }
         @Override
         public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.i(TAG, "On Failure Create Tour");
         }
      });
   }

   private void sendAddStopPoint(AddStopPointRequest addStopPointRequest) {
      APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      Call<ResponseBody> call = apiService.addStopPoint(mApp.getToken(), addStopPointRequest);
      call.enqueue(new Callback<ResponseBody>() {
         @Override
         public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.code() == 200) {
               Log.i(TAG, "On response add stop point");
               try {
                  Log.i(TAG, response.body().string());
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }

         }
         @Override
         public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.i(TAG, "On failure add stop point");
         }
      });
   }
   private String makeURL(List<StopPoint> stopPointList) {
      String url = "https://maps.googleapis.com/maps/api/directions/json";
      String srcAdd = "?origin=" + stopPointList.get(0).getLat() + "," + stopPointList.get(0).getLongitude();
      String desAdd = "&destination=" + stopPointList.get(1).getLat() + "," + stopPointList.get(1).getLongitude();
      String wayPoints = "&waypoints=optimize:true|";
      for (int i = 2; i < stopPointList.size(); i++)
         wayPoints = wayPoints + (wayPoints.equals("&waypoints=optimize:true|") ? "" : "|") + stopPointList.get(i).getLat() + "," + stopPointList.get(i).getLongitude();
      url = url + srcAdd + desAdd + wayPoints + "&key=" + getResources().getString(R.string.api_direct);
      return url;
   }

   private String downloadUrl(String strUrl) throws IOException {
      String data = "";
      InputStream iStream = null;
      HttpURLConnection urlConnection = null;
      try {
         URL url = new URL(strUrl);
         // Creating an http connection to communicate with url
         urlConnection = (HttpURLConnection) url.openConnection();
         // Connecting to url
         urlConnection.connect();
         // Reading data from url
         iStream = urlConnection.getInputStream();
         BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
         StringBuffer sb = new StringBuffer();
         String line = "";
         while ((line = br.readLine()) != null) {
            sb.append(line);
         }
         data = sb.toString();
         Log.d("mylog", "Downloaded URL: " + data.toString());
         br.close();
      } catch (Exception e) {
         Log.d("mylog", "Exception downloading URL: " + e.toString());
      } finally {
         iStream.close();
         urlConnection.disconnect();
      }
      return data;
   }

   /**
    * Method to decode polyline points Courtesy : https://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
    */
   private List<LatLng> decodePoly(String encoded) {
      List<LatLng> poly = new ArrayList<>();
      int index = 0, len = encoded.length();
      int lat = 0, lng = 0;
      while (index < len) {
         int b, shift = 0, result = 0;
         do {
            b = encoded.charAt(index++) - 63;
            result |= (b & 0x1f) << shift;
            shift += 5;
         } while (b >= 0x20);
         int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
         lat += dlat;

         shift = 0;
         result = 0;
         do {
            b = encoded.charAt(index++) - 63;
            result |= (b & 0x1f) << shift;
            shift += 5;
         } while (b >= 0x20);
         int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
         lng += dlng;

         LatLng p = new LatLng((((double) lat / 1E5)),
                 (((double) lng / 1E5)));
         poly.add(p);
      }

      return poly;
   }

   @Override
   public void onBackPressed() {
      if (addStopPointRequest.getStopPoints().size() > 2)
         sendAddStopPoint(addStopPointRequest);
      super.onBackPressed();
   }

   private void getSuggest() {
      mApplication mApp = (mApplication) getApplicationContext();
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
}
