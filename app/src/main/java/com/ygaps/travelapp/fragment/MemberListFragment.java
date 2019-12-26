package com.ygaps.travelapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.ItemListener;
import com.ygaps.travelapp.adapter.MembersAdapter;
import com.ygaps.travelapp.model.Tour;

public class MemberListFragment extends Fragment implements ItemListener {
   private static final String TAG = "MemberListFragment";
   private Tour tour;
   private RecyclerView recyclerView;
   private ImageButton buttonAdd;
   private String memberName;
   public MemberListFragment() {

   }
   public static MemberListFragment newInstance(Tour tour) {
      MemberListFragment fragment = new MemberListFragment();
      fragment.setData(tour);
      return fragment;
   }
   private void setData(Tour tour) {
      this.tour = tour;
   }
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_member, container, false);
   }
   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      recyclerView = getView().findViewById(R.id.recyclerViewMembers);
      buttonAdd = getView().findViewById(R.id.buttonAdd);
      MembersAdapter membersAdapter = new MembersAdapter(getContext(), tour.getMembers(),this);
      recyclerView.setAdapter(membersAdapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
      buttonAdd.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Nhập tên thành viên");

            final EditText input = new EditText(getContext());
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                  memberName = input.getText().toString();
                  DialogFragment dialogFragment = MemberSearchDialogFragment.newInstance(memberName,tour);
                  dialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");
               }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                  dialog.cancel();
               }
            });
            builder.show();
         }
      });
   }
   @Override
   public void onItemClickListener(int pos) {

   }
}
