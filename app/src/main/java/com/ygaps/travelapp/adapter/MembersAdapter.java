package com.ygaps.travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.user.User;

import java.util.List;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MyViewHolder> {
   List<User> memberList;
   Context context;
   ItemListener itemListener;
   public MembersAdapter(Context context, List<User> members, ItemListener itemListener) {
      this.context = context;
      this.memberList = members;
      this.itemListener = itemListener;
   }
   @NonNull
   @Override
   public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(context);
      View itemView = inflater.inflate(R.layout.member, parent, false);
      MyViewHolder viewHolder = new MyViewHolder(itemView);
      return viewHolder;
   }

   @Override
   public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
      User member = memberList.get(position);
      if (member.getName() == null || member.getName().equals(""))
         holder.name.setText("Null");
      else
         holder.name.setText(member.getName());
      if (member.getPhone() == null || member.getPhone().equals(""))
         holder.phone.setText("Null");
      holder.phone.setText(member.getPhone());
      holder.view.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            itemListener.onItemClickListener(holder.getAdapterPosition());
         }
      });
   }

   @Override
   public int getItemCount() {
      return memberList.size();
   }
   class MyViewHolder extends RecyclerView.ViewHolder {
      TextView name, phone;
      ImageView avt;
      View view;
      public MyViewHolder(@NonNull View itemView) {
         super(itemView);
         this.view = itemView;
         name = itemView.findViewById(R.id.textViewName);
         phone = itemView.findViewById(R.id.textViewPhone);
         avt = itemView.findViewById(R.id.profile_image);
      }
   }
}
