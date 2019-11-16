package com.example.hornpub_travel_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hornpub_travel_app.R;
import com.example.hornpub_travel_app.model.Tour;
import com.example.hornpub_travel_app.model.TravelListData;

import java.util.List;

public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.MyViewHolder> {

   List<Tour> tourList;
   private LayoutInflater mLayoutInflater;
   private Context mContext;

   public TravelListAdapter(Context context, List<Tour> tourList) {
      this.tourList = tourList;
      this.mContext = context;
      this.mLayoutInflater = LayoutInflater.from(context);
   }

   public class MyViewHolder extends RecyclerView.ViewHolder {
      public ImageView picture;
      public TextView direction, time, customer, price;

      public MyViewHolder(View itemView) {
         super(itemView);
         picture = itemView.findViewById(R.id.tour_detail_picture);
         direction = itemView.findViewById(R.id.tour_detail_direction);
         time = itemView.findViewById(R.id.tour_detail_time);
         customer = itemView.findViewById(R.id.tour_detail_customers);
         price = itemView.findViewById(R.id.tour_detail_price);

         itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(mContext, direction.getText(), Toast.LENGTH_LONG).show();
            }
         });

         itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               Toast.makeText(mContext, "Long click: " + direction.getText(), Toast.LENGTH_LONG).show();
               return true;
            }
         });
      }
   }

   @NonNull
   @Override
   public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View item = mLayoutInflater.inflate(R.layout.travel_list, parent, false);
      return new MyViewHolder(item);
   }

   @Override
   public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      Tour tour = tourList.get(position);
      holder.picture.setImageResource(R.drawable.demo);
      holder.direction.setText(tour.getName());
      holder.time.setText(tour.getStartDate());
      holder.customer.setText(tour.getAdults()+"");
      holder.price.setText(tour.getMinCost());
   }

   @Override
   public int getItemCount() {
      return tourList.size();
   }
}
