package com.ygaps.travelapp.model.google_map;

import com.ygaps.travelapp.model.google_map.Coordinate;

import java.util.List;

public class CoordinateSet {

   List<Coordinate> coordinateSet;

   public List<Coordinate> getCoordinateSet() {
      return coordinateSet;
   }
   public CoordinateSet(List<Coordinate> coordinateSet) {
      this.coordinateSet = coordinateSet;
   }
}
