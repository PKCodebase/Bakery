package com.Online_Bakery.Services.Impl;

import com.Online_Bakery.Model.Category;
import com.Online_Bakery.Model.Food;
import com.Online_Bakery.Model.Restaurant;
import com.Online_Bakery.Repository.FoodRepository;
import com.Online_Bakery.Requests.CreateFoodRequest;
import com.Online_Bakery.Requests.UpdateFoodReq;
import com.Online_Bakery.Services.FoodService;
import com.Online_Bakery.exception.FoodNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setFood_name(req.getName());
        food.setImages(req.getImages());
        food.setPrice(req.getPrice());
        food.setSeasonal(req.isSeasonal());
        food.setVeg(req.isVeg());

        Food savedFood = foodRepository.save(food);
        restaurant.getFoods().add(savedFood);

        return savedFood;
    }

    @Override
    public String deleteFood(Long foodId) throws Exception {
        Optional<Food> food = foodRepository.findById(foodId);
        if (!food.isPresent()) {
            throw new Exception("Food with ID " + foodId + " not found.");
        }
        foodRepository.delete(food.get());
        return "Food item deleted successfully!";
    }

    @Override
    public List<Food> getFoodByRestaurantId(Long restaurantId, boolean isVeg, boolean isNonVeg, boolean isSeasonal, String category) {
        List<Food> foods = foodRepository.findByRestaurant_RestaurantId(restaurantId);

        if (isVeg) {
            foods = filterByVeg(foods);
        }
        if (isNonVeg) {
            foods = filterByNonVeg(foods);
        }
        if (isSeasonal) {
            foods = filterBySeasonal(foods);
        }
        if (category != null && !category.isEmpty()) {
            foods = filterByCategory(foods, category);
        }
        return foods;
    }

    private List<Food> filterByCategory(List<Food> foods, String category) {
        return foods.stream()
                .filter(food -> food.getFoodCategory() != null && food.getFoodCategory().getName().equals(category))
                .collect(Collectors.toList());
    }

    private List<Food> filterBySeasonal(List<Food> foods) {
        return foods.stream().filter(Food::isSeasonal).collect(Collectors.toList());
    }

    private List<Food> filterByNonVeg(List<Food> foods) {
        return foods.stream().filter(food -> !food.isVeg()).collect(Collectors.toList());
    }

    private List<Food> filterByVeg(List<Food> foods) {
        return foods.stream().filter(Food::isVeg).collect(Collectors.toList());
    }

    @Override
    public List<Food> searchFood(String keyword) {
        List<Food> foods = foodRepository.searchFood(keyword);

        if (foods.isEmpty()) {
            throw new FoodNotFoundException(HttpStatus.NOT_FOUND, "Food not available: " + keyword);
        }

        return foods;
    }


    @Override
    public Optional<Food> findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood = foodRepository.findById(foodId);
        if (!optionalFood.isPresent()) {
            throw new Exception("Food does not exist");
        }
        return optionalFood;
    }

    @Override
    public Food updateAvailabilityStatus(Long foodId) throws Exception {
        Food food = findFoodById(foodId).orElseThrow(() -> new Exception("Food not found"));
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }

    @Override
    public Food updateFood(Long foodId, UpdateFoodReq foodReq) throws Exception {
        Food food = findFoodById(foodId).orElseThrow(() -> new Exception("Food not found"));
        food.setDescription(foodReq.getDescription());
        food.setFood_name(foodReq.getName());
        food.setPrice(foodReq.getPrice());
        food.setImages(foodReq.getImages());
        food.setVeg(foodReq.isVeg());
        return foodRepository.save(food);
    }
}
