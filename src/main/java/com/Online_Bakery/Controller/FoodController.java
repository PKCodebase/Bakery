package com.Online_Bakery.Controller;

import com.Online_Bakery.Model.Food;
import com.Online_Bakery.Model.UserEntity;
import com.Online_Bakery.Services.FoodService;
import com.Online_Bakery.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(name);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getFoodByRestaurantId(@PathVariable Long restaurantId,
                                                            @RequestParam(required = false, defaultValue = "false") boolean veg,
                                                            @RequestParam(required = false, defaultValue = "false") boolean nonVeg,
                                                            @RequestParam(required = false, defaultValue = "false") boolean seasonal,
                                                            @RequestParam(required = false) String foodCategory,
                                                            @RequestHeader("Authorization") String jwt) throws Exception {
        userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getFoodByRestaurantId(restaurantId, veg, nonVeg, seasonal, foodCategory);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
