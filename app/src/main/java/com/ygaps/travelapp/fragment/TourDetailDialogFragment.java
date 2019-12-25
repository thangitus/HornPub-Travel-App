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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.fragment.StopPointDialog.EditStopPointDialogFragment;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourDetailDialogFragment extends Fragment {
   private static final String TAG = "TourDetailDialogFragment";
   private RadioButton radioButton;
   private Tour tour;
   private EditText editTextTourName, editTextAdult, editTextChildren, editTextMinCost, editTextMaxCost;
   private TextView textViewStartDate, textViewEndDate, textViewAvatar;
   private Button buttonDelete, buttonUpdate;
   private Spinner spinnerStopPoints;

   public TourDetailDialogFragment() {
   }

   public static TourDetailDialogFragment newInstance(Tour tour) {
      TourDetailDialogFragment fragment = new TourDetailDialogFragment();
      fragment.setData(tour);
      return fragment;
   }
   public void setData(Tour tour) {
      this.tour = tour;
   }

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_dilog_tour_detail, container, false);
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
            tour.setName(null);
         }
      });
      buttonUpdate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            tour.setName(editTextTourName.getText().toString());
            tour.setStartDate(textViewStartDate.getText().toString());
            tour.setEndDate(textViewEndDate.getText().toString());
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
      textViewStartDate.setText(tour.getStartDate());
      textViewEndDate.setText(tour.getEndDate());
      List<String> stringList = new ArrayList<>();
      stringList.add("Chọn stop point");
      for (int i = 2; i < tour.getStopPoints().size(); i++)
         stringList.add(tour.getStopPoints().get(i).getName());
      ArrayAdapter<String> adapter = new ArrayAdapter<>(MyTourFragment.getInstance().getActivity(), android.R.layout.simple_spinner_item, stringList);
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
            if (response.code() == 200)
               Toast.makeText(MyTourFragment.getInstance().getActivity(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
         }
         @Override
         public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.d("TourDetail", "onFailure");
         }
      });
   }
}
