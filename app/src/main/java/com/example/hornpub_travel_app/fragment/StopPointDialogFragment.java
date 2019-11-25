package com.example.hornpub_travel_app.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.hornpub_travel_app.R;

public class StopPointDialogFragment extends DialogFragment {
   @Override
   public void onStart() {
      super.onStart();
      getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
   }

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.stop_point_infomation_dialog, container,false);
   }

   public StopPointDialogFragment() {

   }

   public static StopPointDialogFragment newInstance(String tittle) {
      StopPointDialogFragment fragment = new StopPointDialogFragment();
      Bundle bundle = new Bundle();
      bundle.putString("tittle", "Stop point");
      fragment.setArguments(bundle);
      return fragment;
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      String title = getArguments().getString("title", "Enter Name");
      getDialog().setTitle(title);
      Log.d("Debug:", "onViewCreated");
      // Show soft keyboard automatically and request focus to field
      getDialog().getWindow().setSoftInputMode(
              WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
   }
}
