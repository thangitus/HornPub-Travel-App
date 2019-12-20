package com.example.hornpub_travel_app.fragment.StopPointDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.application.mApplication;
import com.example.hornpub_travel_app.fragment.MyTourFragment;
import com.example.hornpub_travel_app.model.Tour;
import com.example.hornpub_travel_app.model.create_tour.StopPoint;
import com.example.hornpub_travel_app.network.APIService;
import com.example.hornpub_travel_app.network.NetworkProvider;

import org.json.JSONException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditStopPointDialogFragment extends StopPointDialogFragment {
   private Tour tour;
   private int index;
   public EditStopPointDialogFragment() {

   }
   public static EditStopPointDialogFragment newInstance(Tour tour, int index) {
      EditStopPointDialogFragment fragment = new EditStopPointDialogFragment();
      fragment.setData(tour, index);
      return fragment;
   }
   private void setData(Tour tour, int index) {
      this.index = index;
      this.stopPoint = tour.getStopPoints().get(index);
      this.tour = tour;
   }
   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapping();
      setupView(stopPoint);
      try {
         createSpinnerProvince();
         spinnerProvince.setSelection(stopPoint.getProvinceId() - 1);
         createSpinnerServiceType();
         spinnerServiceType.setSelection(stopPoint.getServiceTypeId() - 1);
      } catch (JSONException e) {
         e.printStackTrace();
      }
      getDialog().setCancelable(false);
      imageButtonClose.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            getDialog().dismiss();
         }
      });
      textViewDateArrive.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            datePicker(textViewDateArrive);
         }
      });
      textViewDateLeave.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            datePicker(textViewDateLeave);
         }
      });
      textViewTimeArrive.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            timePicker(textViewTimeArrive);
         }
      });
      textViewTimeLeave.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            timePicker(textViewTimeLeave);
         }
      });
      buttonAdd.setText(R.string.update);
      buttonList.setText(R.string.remove);
      buttonAdd.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if (getData() != null) {
               APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
               mApplication mApp;
               mApp = (mApplication) getActivity().getApplicationContext();
               String token = mApp.getToken();
               Call<ResponseBody> call = apiService.updateStopPoint(token, tour.getId(), stopPoint);
               call.enqueue(new Callback<ResponseBody>() {
                  @Override
                  public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                     if (response.code() == 200)
                        Toast.makeText(getActivity(), "Update success", Toast.LENGTH_SHORT).show();
                     else Toast.makeText(getActivity(), "Update fail", Toast.LENGTH_SHORT).show();
                     getDialog().dismiss();
                  }
                  @Override
                  public void onFailure(Call<ResponseBody> call, Throwable t) {
                     Toast.makeText(getActivity(), "Update fail", Toast.LENGTH_SHORT).show();
                     getDialog().dismiss();
                  }
               });
            }
         }
      });
      buttonList.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
            mApplication mApp;
            mApp = (mApplication) getActivity().getApplicationContext();
            String token = mApp.getToken();
            Call<ResponseBody> call = apiService.removeStopPoint(token, stopPoint.getId());
            call.enqueue(new Callback<ResponseBody>() {
               @Override
               public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                  if (response.code() == 200)
                     Toast.makeText(getActivity(), "Delete success", Toast.LENGTH_SHORT).show();
                  else Toast.makeText(getActivity(), "Delete fail", Toast.LENGTH_SHORT).show();
                  getDialog().dismiss();
               }
               @Override
               public void onFailure(Call<ResponseBody> call, Throwable t) {
                  Toast.makeText(getActivity(), "Delete fail", Toast.LENGTH_SHORT).show();
                  getDialog().dismiss();
               }
            });
         }
      });
   }
   private void setupView(StopPoint stopPoint) {
      editTextStopPointName.setText(stopPoint.getName());
      editTextAddress.setText(stopPoint.getAddress());
      editTextMinCost.setText(String.valueOf(stopPoint.getMinCost()));
      editTextMaxCost.setText(String.valueOf(stopPoint.getMaxCost()));
      textViewDateArrive.setText(stopPoint.getDateArrive());
      textViewDateLeave.setText(stopPoint.getDateLeave());
      textViewTimeArrive.setText(stopPoint.getTimeArrive());
      textViewTimeLeave.setText(stopPoint.getTimeLeave());
   }

   @Override
   public void onDismiss(@NonNull DialogInterface dialog) {
      super.onDismiss(dialog);
      Fragment parentFragment = MyTourFragment.getInstance();
      ((DialogInterface.OnDismissListener) parentFragment).onDismiss(dialog);
   }
}
