package com.Online_Bakery.Services;

import com.Online_Bakery.DTO.RestaurantDTO;
import com.Online_Bakery.Model.Restaurant;
import com.Online_Bakery.Model.User;
import com.Online_Bakery.Requests.CreateRestaurantReq;

import java.util.List;

public interface RestaurantService {
        public Restaurant createRestaurant(CreateRestaurantReq req, User user);

        public Restaurant updateRestaurant(Long restaurant_id, CreateRestaurantReq updatedRestaurant) throws Exception;

        public void deleteRestaurant(Long restaurant_id) throws Exception;

        public List<Restaurant> getAllRestaurants();

        public List<Restaurant> searchRestaurant(String keyword);

        public Restaurant findRestaurantById(Long restaurant_id) throws Exception;

        public Restaurant getRestaurantByUserId(Long user_id) throws Exception;

        public RestaurantDTO AddToFavorites(Long restaurant_id, User user) throws Exception;

        public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
