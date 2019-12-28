package com.ygaps.travelapp.model.google_map;

import com.ygaps.travelapp.model.create_tour.StopPoint;

import java.util.List;

public class SuggestedDestination {
   List<StopPoint> stopPoints;

   public SuggestedDestination(SuggestedDestination suggestedDestination) {
      this.stopPoints = suggestedDestination.getStopPoints();
   }
   public List<StopPoint> getStopPoints() {
      return stopPoints;
   }

}
