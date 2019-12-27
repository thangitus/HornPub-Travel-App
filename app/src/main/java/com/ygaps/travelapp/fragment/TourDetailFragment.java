package com.ygaps.travelapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.fragment.StopPointDialog.EditStopPointDialogFragment;
import com.ygaps.travelapp.model.DateTimeConvert;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourDetailFragment extends Fragment {
   private static final String TAG = "TourDetailFragment";
   Boolean isDelete;
   private RadioButton radioButton;
   private Tour tour;
   private EditText editTextTourName, editTextAdult, editTextChildren, editTextMinCost, editTextMaxCost;
   private TextView textViewStartDate, textViewEndDate, textViewAvatar;
   private Button buttonDelete, buttonUpdate;
   private Spinner spinnerStopPoints;
   public TourDetailFragment() {
   }

   public static TourDetailFragment newInstance(Tour tour) {
      TourDetailFragment fragment = new TourDetailFragment();
      fragment.setData(tour);
      return fragment;
   }
   public void setData(Tour tour) {
      this.tour = tour;
   }

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_dialog_tour_detail, container, false);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapping();
      setupView();
      setClickListener();
   }
   private void setClickListener() {
      buttonDelete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            isDelete = true;
            tour.setStatus(-1);
            updateTour();
         }
      });
      buttonUpdate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            isDelete = false;
            tour.setName(editTextTourName.getText().toString());
            try {
               tour.setStartDate(DateTimeConvert.DateToMillisecond(textViewStartDate.getText().toString()));
               tour.setEndDate(DateTimeConvert.DateToMillisecond(textViewEndDate.getText().toString()));
            } catch (ParseException e) {
               e.printStackTrace();
            }
            tour.setAdults(Integer.valueOf(editTextAdult.getText().toString()));
            tour.setChilds(Integer.valueOf(editTextChildren.getText().toString()));
            tour.setMinCost(editTextMinCost.getText().toString());
            tour.setMaxCost(editTextMaxCost.getText().toString());
            updateTour();
         }
      });
   }
   private void mapping() {
      View view = getView();
      editTextTourName = view.findViewById(R.id.editTextTourNameDialog);
      editTextAdult = view.findViewById(R.id.editTextAdultsDialog);
      editTextChildren = view.findViewById(R.id.editTextChildrenDialog);
      editTextMinCost = view.findViewById(R.id.editTextMinCostDialog);
      editTextMaxCost = view.findViewById(R.id.editTextMaxCostDialog);
      textViewStartDate = view.findViewById(R.id.textViewStartDateDialog);
      textViewEndDate = view.findViewById(R.id.textViewEndDateDialog);
      textViewAvatar = view.findViewById(R.id.textViewIMG);
      radioButton = view.findViewById(R.id.radioButtonPrivateDialog);
      buttonDelete = view.findViewById(R.id.buttonDelete);
      buttonUpdate = view.findViewById(R.id.buttonUpdate);
      spinnerStopPoints = view.findViewById(R.id.spinnerStopPoint);
   }

   private void setupView() {
      editTextTourName.setText(tour.getName());
      editTextAdult.setText(String.valueOf(tour.getAdults()));
      editTextChildren.setText(String.valueOf(tour.getChilds()));
      editTextMinCost.setText(tour.getMinCost());
      editTextMaxCost.setText(tour.getMaxCost());
      textViewStartDate.setText(DateTimeConvert.MillisecondToDate(tour.getStartDate()));
      textViewEndDate.setText(DateTimeConvert.MillisecondToDate(tour.getEndDate()));

      mApplication mApp;
      mApp = (mApplication) getActivity().getApplicationContext();
      String userId = mApp.getUserId();
      if (!userId.equals(String.valueOf(tour.getMembers().get(0).getId()))) {
         buttonDelete.setVisibility(View.GONE);
         buttonUpdate.setVisibility(View.GONE);
         radioButton.setVisibility(View.GONE);
      }

      List<String> stringList = new ArrayList<>();
      stringList.add("Chọn stop point");
      for (int i = 0; i < tour.getStopPoints().size(); i++)
         stringList.add(tour.getStopPoints().get(i).getName());
      ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, stringList);
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinnerStopPoints.setAdapter(adapter);
      spinnerStopPoints.setSelection(0);
      spinnerStopPoints.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (i == 0) return;
            int index = i - 1;
            DialogFragment dialogFragment = EditStopPointDialogFragment.newInstance(tour, index);
            dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");
         }
         @Override
         public void onNothingSelected(AdapterView<?> adapterView) {

         }
      });
   }

   private void updateTour() {
      APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      mApplication mApp;
      mApp = (mApplication) getActivity().getApplicationContext();
      String token = mApp.getToken();
      Call<ResponseBody> call = apiService.updateTour(token, this.tour);
      call.enqueue(new Callback<ResponseBody>() {
         @Override
         public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.code() == 200) {
               Log.i(TAG, "Success");
               if (isDelete) getActivity().finish();
            }
         }
         @Override
         public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.d("TourDetail", "onFailure");
         }
      });
   }
}
