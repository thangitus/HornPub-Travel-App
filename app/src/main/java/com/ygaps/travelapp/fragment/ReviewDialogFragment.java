package com.ygaps.travelapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.model.ReviewTourRequest;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewDialogFragment extends DialogFragment {
   public static String TAG = "ReviewDialogFragment";
   private ImageButton buttonClose;
   private TextView textViewTittle, textViewReviewTitle;
   private EditText editTextComment;
   private Button buttonSubmit;
   private RatingBar ratingBar;
   private Boolean isTour;
   private int tourId;
   public ReviewDialogFragment() {
   }
   public static ReviewDialogFragment newInstance(Boolean isTour, int tourId) {
      ReviewDialogFragment fragment = new ReviewDialogFragment();
      fragment.setData(isTour, tourId);
      return fragment;
   }
   private void setData(Boolean isTour, int tourId) {
      this.isTour = isTour;
      this.tourId = tourId;
   }
   @Override
   public void onStart() {
      super.onStart();
      getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
   }

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_dialog_review, container, false);
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
      buttonClose.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            getDialog().dismiss();
         }
      });
      buttonSubmit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            if (checkData()) {
               mApplication mApp;
               mApp = (mApplication) getActivity().getApplicationContext();
               String token = mApp.getToken();
               ReviewTourRequest reviewTourRequest = new ReviewTourRequest(tourId, (int) ratingBar.getRating(), editTextComment.getText().toString());
               APIService apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
               Call<ResponseBody> call = apiService.reviewTour(token, reviewTourRequest);
               call.enqueue(new Callback<ResponseBody>() {
                  @Override
                  public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                     if (response.code() == 200)
                        Log.i(TAG, "Review success, id: " + tourId);
                     getDialog().dismiss();
                  }
                  @Override
                  public void onFailure(Call<ResponseBody> call, Throwable t) {
                     Log.i(TAG, "Review fail");
                     getDialog().dismiss();
                  }
               });
            }
         }
      });
   }
   private boolean checkData() {
      if (ratingBar.getRating() == 0) {
         Toast.makeText(getActivity(), "Vui lòng chọn số sao", Toast.LENGTH_SHORT).show();
         return false;
      }
      if (editTextComment.getText().equals("")) {
         Toast.makeText(getActivity(), "Vui lòng nhập bình luận", Toast.LENGTH_SHORT).show();
         return false;
      }
      return true;
   }

   private void mapping() {
      buttonClose = getView().findViewById(R.id.buttonCloseDialog);
      textViewReviewTitle = getView().findViewById(R.id.textViewReviewTitle);
      textViewTittle = getView().findViewById(R.id.textViewTitleReview);
      editTextComment = getView().findViewById(R.id.editTextComment);
      buttonSubmit = getView().findViewById(R.id.buttonSubmitReview);
      ratingBar = getView().findViewById(R.id.ratingBarReview);
   }
   private void setupView() {
      if (isTour) {
         textViewTittle.setText("Review tour");
         textViewReviewTitle.setText("Rate this tour");
      } else {
         textViewTittle.setText("Review stop point");
         textViewReviewTitle.setText("Rate this stop point");
      }
   }
}
