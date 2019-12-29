package com.ygaps.travelapp.fragment.StopPointDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ygaps.travelapp.model.create_tour.AddStopPointRequest;
import com.ygaps.travelapp.model.create_tour.StopPoint;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class AddStopPointDialogFragment extends StopPointDialogFragment {
   private AddStopPointRequest addStopPointRequest;
   private String province;
   private int change = -1;

   public AddStopPointDialogFragment() {

   }
   public static AddStopPointDialogFragment newInstance(AddStopPointRequest addStopPointRequest, StopPoint stopPoint, String province) {
      AddStopPointDialogFragment fragment = new AddStopPointDialogFragment();
      fragment.setData(addStopPointRequest, stopPoint, province);
      return fragment;
   }
   private void setData(AddStopPointRequest addStopPointRequest, StopPoint stopPoint, String province) {
      this.addStopPointRequest = addStopPointRequest;
      this.stopPoint = stopPoint;
      this.province = province;
   }
   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);

      buttonAdd.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if (getData() != null)
               getDialog().dismiss();
         }
      });
      buttonList.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            showListStopPoint();
         }
      });
   }
   @Override
   public void onDismiss(@NonNull DialogInterface dialog) {
      super.onDismiss(dialog);
      if (stopPoint.getName() == null) {
         return;
      }
      if (change != -1)
         addStopPointRequest.getStopPoints().remove(change);
      addStopPointRequest.getStopPoints().add(stopPoint);
      final Activity activity = getActivity();
      if (activity instanceof DialogInterface.OnDismissListener) {
         ((DialogInterface.OnDismissListener) activity).onDismiss(dialog);
      }
   }
   private void showListStopPoint() {
      if (addStopPointRequest.getStopPoints().size() < 1) return;
      final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      builder.setTitle("List stop point");
      List<String> nameStopPoints = new ArrayList<>();
      for (int i = 0; i < addStopPointRequest.getStopPoints().size(); i++) {
         nameStopPoints.add(addStopPointRequest.getStopPoints().get(i).getName());
      }
      CharSequence[] cs = nameStopPoints.toArray(new CharSequence[nameStopPoints.size()]);
      builder.setItems(cs, new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {
            stopPoint = addStopPointRequest.getStopPoints().get(i);
            editTextStopPointName.setText(stopPoint.getName());
            editTextAddress.setText(stopPoint.getAddress());
            change = i;
         }
      });
      builder.show();
   }
   @Override
   protected void createSpinnerProvince() throws JSONException {
      super.createSpinnerProvince();
      List<String> stringList = readJSON("province");
      if (province != null) {
         province = province.replace("Province ", "");
         for (int i = 0; i < stringList.size(); i++)
            if (stringList.get(i).equals(province))
               spinnerProvince.setSelection(i);
      }
   }
}
