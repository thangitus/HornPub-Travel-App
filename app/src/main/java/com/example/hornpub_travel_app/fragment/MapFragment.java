package com.example.hornpub_travel_app.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hornpub_travel_app.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

   private static View view;

   public MapFragment() {
      // Required empty public constructor
   }


   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      if (view != null) {
         ViewGroup parent = (ViewGroup) view.getParent();
         if (parent != null)
            parent.removeView(view);
      }
      try {
         view = inflater.inflate(R.layout.fragment_map, container, false);
      } catch (InflateException e) {
         /* map is already there, just return view as it is */
      }
      return view;

   }
}
