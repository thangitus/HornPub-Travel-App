package com.ygaps.travelapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.ItemListener;
import com.ygaps.travelapp.adapter.ReviewAdapter;
import com.ygaps.travelapp.model.Comment;

import java.util.List;

public class ReviewListFragment extends Fragment implements ItemListener {
   TextView numberRate, textViewNoReview, textViewRating;
   RatingBar ratingBar;
   RecyclerView recyclerView;
   List<Comment> reviewLists;
   ImageButton buttonAddReview;
   int tourId;
   public ReviewListFragment() {
   }
   public static ReviewListFragment newInstance(List<Comment> comments, int tourId) {
      ReviewListFragment fragment = new ReviewListFragment();
      fragment.setData(comments, tourId);
      return fragment;
   }

   private void setData(List<Comment> comments, int tourId) {
      this.reviewLists = comments;
      this.tourId = tourId;
   }

   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_list_review, container, false);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      mapping();
      setupView();
      buttonAddReview.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            DialogFragment dialogFragment = ReviewDialogFragment.newInstance(true, tourId);
            dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");
         }
      });
   }

   private void mapping() {
      numberRate = getView().findViewById(R.id.textViewNumberOfRating);
      ratingBar = getView().findViewById(R.id.ratingBar);
      recyclerView = getView().findViewById(R.id.recyclerViewComment);
      textViewNoReview = getView().findViewById(R.id.textViewNoReview);
      textViewRating = getView().findViewById(R.id.textView);
      buttonAddReview=getView().findViewById(R.id.buttonAddReview);
   }

   private void setupView() {
      if (reviewLists.size() == 0) {
         numberRate.setVisibility(View.GONE);
         ratingBar.setVisibility(View.GONE);
         textViewRating.setVisibility(View.GONE);
         textViewNoReview.setVisibility(View.VISIBLE);
         return;
      }
      numberRate.setText(reviewLists.size() + " Reviews");
      int sum = 0;
      for (int i = 0; i < reviewLists.size(); i++)
         sum += reviewLists.get(i).getPoint();
      ratingBar.setRating(sum / reviewLists.size());
      ReviewAdapter reviewAdapter = new ReviewAdapter(getContext(), reviewLists, this);
      recyclerView.setAdapter(reviewAdapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
   }
   @Override
   public void onItemClickListener(int pos) {

   }
}
