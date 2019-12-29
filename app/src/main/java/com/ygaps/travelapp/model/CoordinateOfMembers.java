package com.ygaps.travelapp.model;

import com.ygaps.travelapp.model.google_map.Coordinate;

import java.util.List;

public class CoordinateOfMembers {
   List<Coordinate> coordinates;
   public CoordinateOfMembers(CoordinateOfMembers coordinateOfMembers) {
      this.coordinates = coordinateOfMembers.getCoordinates();
   }
   public List<Coordinate> getCoordinates() {
      return coordinates;
   }
   public void setCoordinates(List<Coordinate> coordinates) {
      this.coordinates = coordinates;
   }
}
