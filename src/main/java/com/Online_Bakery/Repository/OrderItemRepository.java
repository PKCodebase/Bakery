package com.Online_Bakery.Repository;

import com.Online_Bakery.Model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {

}
