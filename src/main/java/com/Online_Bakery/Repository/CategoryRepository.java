package com.Online_Bakery.Repository;


import com.Online_Bakery.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
        List<Category> findByRestaurant_RestaurantId(Long id);
}
