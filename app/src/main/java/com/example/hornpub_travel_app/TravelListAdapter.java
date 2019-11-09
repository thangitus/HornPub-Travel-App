package com.example.hornpub_travel_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.MyViewHolder> {

    private List<TravelListData> travelList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public TravelListAdapter(Context context, List<TravelListData> travelList) {
        this.travelList = travelList;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView direction, time, customer, price;

        public MyViewHolder(View itemView) {
            super(itemView);
            picture = (ImageView) itemView.findViewById(R.id.tour_detail_picture);
            direction = (TextView) itemView.findViewById(R.id.tour_detail_direction);
            time = (TextView) itemView.findViewById(R.id.tour_detail_time);
            customer = (TextView) itemView.findViewById(R.id.tour_detail_customers);
            price = (TextView) itemView.findViewById(R.id.tour_detail_price);

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
        TravelListData travelListData = travelList.get(position);
        holder.picture.setImageResource(R.drawable.demo);
        holder.direction.setText(travelListData.getDirection());
        holder.time.setText(travelListData.getTime());
        holder.customer.setText(travelListData.getCustomer());
        holder.price.setText(travelListData.getPrice());
    }

    @Override
    public int getItemCount() {
        return travelList.size();
    }
}
