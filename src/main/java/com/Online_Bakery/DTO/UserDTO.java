//package com.Online_Bakery.DTO;
//
//import com.Online_Bakery.Model.User;
//import com.Online_Bakery.Model.Restaurant;
//import lombok.Data;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Data
//public class UserDTO {
//    private Long id;
//    private String username;
//    private String email;
//    private String role;
//    private List<String> favoriteRestaurants;
//
//    public UserDTO(User user) {
//        this.id = user.getId();
//        this.username = user.getUsername();
//        this.email = user.getEmail();
//        this.role = user.getRole().name();
//        this.favoriteRestaurants = user.getFavorites().stream().map(Restaurant::getRestaurant_name).collect(Collectors.toList());
//    }
//}
