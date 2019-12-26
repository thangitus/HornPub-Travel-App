package com.ygaps.travelapp.model;

import com.ygaps.travelapp.model.user.User;

import java.util.List;

public class SearchUserResponse {
   List<User> users;
   public List<User> getUsers() {
      return users;
   }
   public void setUsers(List<User> users) {
      this.users = users;
   }
   public SearchUserResponse(SearchUserResponse searchUserResponse) {
      this.users = searchUserResponse.getUsers();
   }
}
