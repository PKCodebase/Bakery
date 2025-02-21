package com.Online_Bakery.Controller;

import com.Online_Bakery.Model.Food;
import com.Online_Bakery.Model.Restaurant;
import com.Online_Bakery.Model.UserEntity;
import com.Online_Bakery.Requests.CreateFoodRequest;
import com.Online_Bakery.Requests.UpdateFoodReq;
import com.Online_Bakery.Response.MessageResponse;
import com.Online_Bakery.Services.FoodService;
import com.Online_Bakery.Services.RestaurantService;
import com.Online_Bakery.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(req.getRestaurantId());
        Food food = foodService.createFood(req, req.getCategory(), restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@RequestHeader("Authorization") String jwt,
                                                      @PathVariable Long id) {
        try {
            UserEntity user = userService.findUserByJwtToken(jwt);


            if (!"ADMIN".equals(user.getRole())) {
                return new ResponseEntity<>(new MessageResponse("Unauthorized action"), HttpStatus.FORBIDDEN);
            }

            foodService.deleteFood(id);
            return ResponseEntity.ok(new MessageResponse("Food deleted successfully"));

        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateAvailabilityStatus(@RequestHeader("Authorization") String jwt,
                                                         @PathVariable Long id) throws Exception {
        userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailabilityStatus(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Food> updateFood(@RequestHeader("Authorization") String jwt,
                                           @PathVariable Long id,
                                           @RequestBody UpdateFoodReq foodReq) throws Exception {
        userService.findUserByJwtToken(jwt);
        Food food = foodService.updateFood(id, foodReq);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
