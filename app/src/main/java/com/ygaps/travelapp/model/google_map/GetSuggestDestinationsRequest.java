package com.ygaps.travelapp.model.google_map;

import java.util.List;

public class GetSuggestDestinationsRequest {

   boolean hasOneCoordinate;

   List<CoordinateSet> coordList;

   public GetSuggestDestinationsRequest(boolean hasOneCoordinate, List<CoordinateSet> coordList) {
      this.hasOneCoordinate = hasOneCoordinate;
      this.coordList = coordList;
   }

}
