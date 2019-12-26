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
import com.ygaps.travelapp.model.DateTimeConvert;
import com.ygaps.travelapp.model.Tour;

import java.util.List;

public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.MyViewHolder> {

   List<Tour> tourList;
   ItemListener tourListener;
   private LayoutInflater mLayoutInflater;
   private Context mContext;
   public TravelListAdapter(Context context, List<Tour> tourList, ItemListener tourListener) {
      this.tourList = tourList;
      this.mContext = context;
      this.tourListener = tourListener;
      this.mLayoutInflater = LayoutInflater.from(context);
   }
   @NonNull
   @Override
   public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View item = mLayoutInflater.inflate(R.layout.travel_list, parent, false);
      return new MyViewHolder(item);
   }
   @Override
   public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
      Tour tour = tourList.get(position);
      holder.picture.setImageResource(R.drawable.demo);
      holder.direction.setText(tour.getName());
      if (tour.getStartDate() != null && tour.getEndDate() != null)
         holder.time.setText(DateTimeConvert.MillisecondToDate(tour.getStartDate()) + " - " + DateTimeConvert.MillisecondToDate(tour.getEndDate()));
      holder.customer.setText(String.valueOf(tour.getAdults()));
      holder.price.setText(tour.getMinCost());
      holder.view.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            tourListener.onItemClickListener(holder.getAdapterPosition());
         }
      });
   }
   @Override
   public int getItemCount() {
      return tourList.size();
   }

   class MyViewHolder extends RecyclerView.ViewHolder {
      public ImageView picture;
      public TextView direction, time, customer, price;
      View view;
      public MyViewHolder(View itemView) {
         super(itemView);
         this.view = itemView;
         picture = itemView.findViewById(R.id.tour_detail_picture);
         direction = itemView.findViewById(R.id.tour_detail_direction);
         time = itemView.findViewById(R.id.tour_detail_time);
         customer = itemView.findViewById(R.id.tour_detail_customers);
         price = itemView.findViewById(R.id.tour_detail_price);
      }

   }
}
