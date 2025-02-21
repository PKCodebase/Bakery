package com.Online_Bakery.Services;

import com.Online_Bakery.Model.Category;
import com.Online_Bakery.Model.Food;
import com.Online_Bakery.Model.Restaurant;
import com.Online_Bakery.Requests.CreateFoodRequest;
import com.Online_Bakery.Requests.UpdateFoodReq;

import java.util.List;
import java.util.Optional;

public interface FoodService {
    Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant);

    String deleteFood(Long foodId) throws Exception;

    List<Food> getFoodByRestaurantId(Long restaurantId, boolean isVeg, boolean isNonVeg, boolean isSeasonal, String category);

    List<Food> searchFood(String keyword);

    Optional<Food> findFoodById(Long foodId) throws Exception;

    Food updateAvailabilityStatus(Long foodId) throws Exception;

    Food updateFood(Long foodId, UpdateFoodReq req) throws Exception;
}
