package com.ygaps.travelapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.adapter.ItemListener;
import com.ygaps.travelapp.adapter.MembersAdapter;
import com.ygaps.travelapp.application.mApplication;
import com.ygaps.travelapp.model.SearchUserResponse;
import com.ygaps.travelapp.model.Tour;
import com.ygaps.travelapp.model.invite_member.InviteMemberRequest;
import com.ygaps.travelapp.model.user.User;
import com.ygaps.travelapp.network.APIService;
import com.ygaps.travelapp.network.NetworkProvider;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberSearchDialogFragment extends DialogFragment implements ItemListener {
   private static final String TAG = "MemberSearchDialog";
   RecyclerView recyclerView;
   ImageButton imageButtonClose;
   List<User> users;
   String search;
   APIService apiService;
   Tour tour;
   public MemberSearchDialogFragment() {

   }
   public static MemberSearchDialogFragment newInstance(String search, Tour tour) {
      MemberSearchDialogFragment memberSearchDialogFragment = new MemberSearchDialogFragment();
      memberSearchDialogFragment.setData(search, tour);
      return memberSearchDialogFragment;
   }
   private void setData(String search, Tour tour) {
      this.search = search;
      this.tour = tour;
   }
   @Override
   public void onStart() {
      super.onStart();
      getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
   }

   @Nullable
   @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      return inflater.inflate(R.layout.fragment_dialog_add_member, container, false);
   }
   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      recyclerView = getView().findViewById(R.id.recyclerViewMembersSearch);
      apiService = NetworkProvider.getInstance().getRetrofit().create(APIService.class);
      Call<SearchUserResponse> call = apiService.searchUsers(search, 1, "10");
      call.enqueue(new Callback<SearchUserResponse>() {
         @Override
         public void onResponse(Call<SearchUserResponse> call, Response<SearchUserResponse> response) {
            SearchUserResponse searchUserResponse = new SearchUserResponse(response.body());
            users = searchUserResponse.getUsers();
            createRecyclerView(users);
         }
         @Override
         public void onFailure(Call<SearchUserResponse> call, Throwable t) {

         }
      });

      imageButtonClose = getView().findViewById(R.id.buttonCloseDialog);
      imageButtonClose.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            getDialog().dismiss();
         }
      });

   }
   private void createRecyclerView(List<User> users) {
      MembersAdapter membersAdapter = new MembersAdapter(getContext(), users, this);
      recyclerView.setAdapter(membersAdapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
   }
   @Override
   public void onItemClickListener(int pos) {
      mApplication mApp;
      mApp = (mApplication) getActivity().getApplicationContext();
      String token = mApp.getToken();
      InviteMemberRequest inviteMemberRequest = new InviteMemberRequest(String.valueOf(tour.getId()), String.valueOf(users.get(pos).getId()), tour.getIsPrivate());
      Call<ResponseBody> call = apiService.inviteMember(token, inviteMemberRequest);
      call.enqueue(new Callback<ResponseBody>() {
         @Override
         public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.code() == 200)
               Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
            else Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
            getDialog().dismiss();
         }
         @Override
         public void onFailure(Call<ResponseBody> call, Throwable t) {

         }
      });
   }
}
