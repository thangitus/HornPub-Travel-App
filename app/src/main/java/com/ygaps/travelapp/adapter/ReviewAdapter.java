package com.ygaps.travelapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ygaps.travelapp.R;
import com.ygaps.travelapp.model.Comment;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
   List<Comment> comments;
   Context context;
   ItemListener itemListener;

   public ReviewAdapter(Context context, List<Comment> comments, ItemListener itemListener) {
      this.context = context;
      this.comments = comments;
      this.itemListener = itemListener;
   }
   @NonNull
   @Override
   public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(context);
      View itemView = inflater.inflate(R.layout.review_item, parent, false);
      MyViewHolder viewHolder = new MyViewHolder(itemView);
      return viewHolder;
   }

   @Override
   public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
      Comment comment = comments.get(position);
      if (comment.getName() == null || comment.getName().equals(""))
         holder.name.setText("Null");
      else
         holder.name.setText(comment.getName());

      if (comment.getAvatar() != null && !comment.getAvatar().equals(""))
         Glide.with(context).load(comment.getAvatar()).into(holder.avt);
      holder.ratingBar.setRating(comment.getPoint());
      holder.textViewComment.setText(comment.getReview());
      holder.view.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            itemListener.onItemClickListener(holder.getAdapterPosition());
         }
      });
   }

   @Override
   public int getItemCount() {
      return comments.size();
   }
   class MyViewHolder extends RecyclerView.ViewHolder {
      TextView name, textViewComment;
      ImageView avt;
      RatingBar ratingBar;
      View view;
      public MyViewHolder(@NonNull View itemView) {
         super(itemView);
         this.view = itemView;
         ratingBar = itemView.findViewById(R.id.ratingBarCmt);
         name = itemView.findViewById(R.id.textViewNameCmt);
         textViewComment = itemView.findViewById(R.id.textViewCmt);
         avt = itemView.findViewById(R.id.imageViewAvatarCmt);
      }
   }
}
