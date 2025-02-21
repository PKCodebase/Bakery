package com.Online_Bakery.Controller;

import com.Online_Bakery.DTO.RestaurantDTO;
import com.Online_Bakery.Model.Restaurant;
import com.Online_Bakery.Model.UserEntity;

import com.Online_Bakery.Services.RestaurantService;
import com.Online_Bakery.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController{
    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurant(@RequestHeader("Authorization")
                                                       String jwt,
                                                       @RequestParam String keyword) throws Exception
    {
        UserEntity user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurant = restaurantService.searchRestaurant(keyword);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants(@RequestHeader("Authorization") String jwt) throws Exception
    {
        UserEntity user = userService.findUserByJwtToken(jwt);
        List<Restaurant> restaurant = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(@RequestHeader("Authorization") String jwt,@PathVariable Long id) throws Exception
    {
        UserEntity user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorites")
    public ResponseEntity<RestaurantDTO> addToFavorites(@RequestHeader("Authorization") String jwt, @PathVariable Long id) throws Exception
    {
        UserEntity user = userService.findUserByJwtToken(jwt);
        RestaurantDTO restaurant = restaurantService.AddToFavorites(id, user);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

}
