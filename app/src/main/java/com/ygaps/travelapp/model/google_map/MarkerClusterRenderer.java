package com.ygaps.travelapp.model.google_map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.ygaps.travelapp.R;

public class MarkerClusterRenderer extends DefaultClusterRenderer<MyItem> {   // 1
   private static final int MARKER_DIMENSION = 48;  // 2
   private final ImageView markerImageView;
   private Context context;
   public MarkerClusterRenderer(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
      super(context, map, clusterManager);
      this.context = context;
      markerImageView = new ImageView(context);
      markerImageView.setLayoutParams(new ViewGroup.LayoutParams(MARKER_DIMENSION, MARKER_DIMENSION));
   }

   @Override
   protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
      super.onBeforeClusterItemRendered(item, markerOptions);
      int serviceTypeId = item.getServiceTypeId();
      BitmapDescriptor icon;

      if (serviceTypeId == 1)
         icon = bitmapDescriptorFromVector(context, R.drawable.ic_food_and_restaurant);
      else if (serviceTypeId == 2)
         icon = bitmapDescriptorFromVector(context, R.drawable.ic_hotel);
      else if (serviceTypeId == 3)
         icon = bitmapDescriptorFromVector(context, R.drawable.ic_bed);
      else
         icon = bitmapDescriptorFromVector(context, R.drawable.ic_favorite);
      markerOptions.icon(icon);

   }

   private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
      Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
      vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
      Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);
      vectorDrawable.draw(canvas);
      return BitmapDescriptorFactory.fromBitmap(bitmap);
   }
}