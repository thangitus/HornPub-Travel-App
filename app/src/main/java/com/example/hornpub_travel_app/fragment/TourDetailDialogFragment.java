package com.example.hornpub_travel_app.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.application.mApplication;
import com.example.hornpub_travel_app.model.Tour;
import com.example.hornpub_travel_app.network.APIService;
import com.example.hornpub_travel_app.network.NetworkProvider;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourDetailDialogFragment extends DialogFragment {
   private static final String TAG = "TourDetailDialogFragment";
   RadioButton radioButton;
   private Tour tour;
   private EditText editTextTourName, editTextAdult, editTextChildren, editTextMinCost, editTextMaxCost;
   private TextView textViewStartDate, textViewEndDate, textViewAvatar;
   private ImageButton imageButtonClose;
   private Button buttonDelete, buttonUpdate;
   public static TourDetailDialogFragment newInstance(Tour tour) {
      TourDetailDialogFragment fragment = new TourDetailDialogFragment();
      fragment.setData(tour);
      return fragment;
   }
   public void setData(Tour tour) {
      this.tour = tour;
   }

   @Override
   public void onStart() {
      super.onStart();
      getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
   }
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_dilog_tour_detail, container, false);
   }
   @Override
   public void onActivityCreated(@Nullable Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
      getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
   }
   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapping();
      setupView();
      setClickListener();
   }
   private void setClickListener() {
      imageButtonClose.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            getDialog().dismiss();
         }
      });
      buttonDelete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            tour.setName(null);
            getDialog().dismiss();
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
            getDialog().dismiss();
         }
      });
   }
   private void setupView() {
      editTextTourName.setText(tour.getName());
      editTextAdult.setText(String.valueOf(tour.getAdults()));
      editTextChildren.setText(String.valueOf(tour.getChilds()));
      editTextMinCost.setText(tour.getMinCost());
      editTextMaxCost.setText(tour.getMaxCost());
      textViewStartDate.setText(tour.getStartDate());
      textViewEndDate.setText(tour.getEndDate());

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
      imageButtonClose = view.findViewById(R.id.buttonCloseDialog);
      buttonDelete = view.findViewById(R.id.buttonDelete);
      buttonUpdate = view.findViewById(R.id.buttonUpdate);
   }

   @Override
   public void onDismiss(@NonNull DialogInterface dialog) {
      super.onDismiss(dialog);
      Fragment parentFragment = MyTourFragment.getInstance();
      ((DialogInterface.OnDismissListener) parentFragment).onDismiss(dialog);
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
         if(response.code()==200)
            Toast.makeText(MyTourFragment.getInstance().getActivity(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
         }
         @Override
         public void onFailure(Call<ResponseBody> call, Throwable t) {
            Log.d("TourDetail","onFailure");
         }
      });
   }
}
