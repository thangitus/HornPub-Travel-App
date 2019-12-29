package com.ygaps.travelapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.create_tour.StopPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class StopPointInfoFragment extends Fragment {
   StopPoint stopPoint;
   TextView textViewName, textViewAddress, textViewMin, textViewMax, textViewService, textViewProvince;

   public StopPointInfoFragment() {
   }

   public static StopPointInfoFragment newInstance(StopPoint stopPoint) {
      StopPointInfoFragment fragment = new StopPointInfoFragment();
      fragment.setData(stopPoint);
      return fragment;
   }
   public void setData(StopPoint stopPoint) {
      this.stopPoint = stopPoint;
   }

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_stop_point_detail, container, false);
   }
   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapping();
      try {
         setupView();
      } catch (JSONException e) {
         e.printStackTrace();
      }
   }

   private void mapping() {
      textViewName = getView().findViewById(R.id.textViewStopPointNameDetail);
      textViewAddress = getView().findViewById(R.id.textViewAddressDetail);
      textViewMin = getView().findViewById(R.id.textViewMinCostDetail);
      textViewMax = getView().findViewById(R.id.textViewMaxCostDetail);
      textViewService = getView().findViewById(R.id.textViewServiceTypeDetail);
      textViewProvince = getView().findViewById(R.id.textViewProvinceDetail);
   }
   private void setupView() throws JSONException {
      if (stopPoint.getName() != null)
         textViewName.setText(stopPoint.getName());
      if (stopPoint.getAddress() != null)
         textViewAddress.setText(stopPoint.getAddress());
      textViewMin.setText(String.valueOf(stopPoint.getMinCost()));
      textViewMax.setText(String.valueOf(stopPoint.getMaxCost()));
      setProvince();
      int serviceType = stopPoint.getServiceTypeId();
      if (serviceType == 1)
         textViewService.setText("Restaurant");
      else if (serviceType == 2)
         textViewService.setText("Hotel");
      else if (serviceType == 4)
         textViewService.setText("Rest Station");
      else
         textViewService.setText("Other");
   }

   private void setProvince() throws JSONException {
      String name = "province";
      String jsonString = "";
      try {
         InputStream is = getResources().openRawResource(getResources().getIdentifier(name, "raw", getActivity().getPackageName()));
         int size = is.available();
         byte[] buffer = new byte[size];
         is.read(buffer);
         is.close();
         jsonString = new String(buffer, "UTF-8");
      } catch (IOException ex) {
         ex.printStackTrace();
      }
      JSONArray jsonArray = new JSONArray(jsonString);
      JSONObject json_data = jsonArray.getJSONObject(stopPoint.getProvinceId() - 1);
      textViewProvince.setText(json_data.getString("title"));
   }
}
