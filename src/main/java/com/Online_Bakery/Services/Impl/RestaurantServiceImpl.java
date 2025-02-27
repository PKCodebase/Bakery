package com.Online_Bakery.Services.Impl;

import com.Online_Bakery.Model.Address;
import com.Online_Bakery.Model.Restaurant;
import com.Online_Bakery.Model.User;
import com.Online_Bakery.Repository.AddressRepo;
import com.Online_Bakery.Repository.RestaurantRepo;
import com.Online_Bakery.Repository.UserRepository;
import com.Online_Bakery.Requests.CreateRestaurantReq;
import com.Online_Bakery.Services.RestaurantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Restaurant createRestaurant(CreateRestaurantReq req, User user) {

        if (req.getAddress() == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        if (req.getName() == null || req.getName().isEmpty()) {
            throw new IllegalArgumentException("Restaurant name cannot be null or empty");
        }
        if (req.getContactInformation() == null) {
            throw new IllegalArgumentException("Contact Information cannot be null");
        }

        // Save Address
        Address address = addressRepo.save(req.getAddress());

        // Create and save restaurant
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurant_address(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImagesList() != null ? req.getImagesList() : new ArrayList<>());
        restaurant.setRestaurant_name(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepo.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurant_id, CreateRestaurantReq updatedRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurant_id);

        if (updatedRestaurant.getCuisineType() != null) {
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }

        if (updatedRestaurant.getDescription() != null) {
            restaurant.setDescription(updatedRestaurant.getDescription());
        }

        if (updatedRestaurant.getName() != null) {
            restaurant.setRestaurant_name(updatedRestaurant.getName());
        }

        if (updatedRestaurant.getContactInformation() != null) {
            restaurant.setContactInformation(updatedRestaurant.getContactInformation());
        }

        return restaurantRepo.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurant_id) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurant_id);
        restaurantRepo.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepo.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepo.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurant_id) throws Exception {
        return restaurantRepo.findById(restaurant_id)
                .orElseThrow(() -> new Exception("Restaurant not found with id " + restaurant_id));
    }

    @Override
    public Restaurant getRestaurantByUserId(Long user_id) throws Exception {
        Restaurant restaurant = restaurantRepo.findByOwnerId(user_id);
        if (restaurant == null) {
            throw new Exception("Restaurant not found with owner_id " + user_id);
        }
        return restaurant;
    }

    @Override
    @Transactional
    public Restaurant AddToFavorites(Long restaurant_id, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurant_id);

        boolean isFavorite = user.getFavorites().contains(restaurant);

        if (isFavorite) {
            user.getFavorites().remove(restaurant);
        } else {
            user.getFavorites().add(restaurant);
        }

        userRepository.save(user);
        return restaurant;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepo.save(restaurant);
    }
}
