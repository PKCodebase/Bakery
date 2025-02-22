package com.Online_Bakery.Services;

import com.Online_Bakery.Model.Order;
import com.Online_Bakery.Model.User;
import com.Online_Bakery.Requests.OrderRequest;

import java.util.List;

public interface OrderService {
    public Order createOrder(OrderRequest order, User user) throws Exception;

    public Order updateOrder(Long orderId, String status) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUserOrders(Long userId) throws Exception;

    public List<Order> getRestaurantOrders(Long restaurantId, String orderStatus) throws Exception;

    public Order findOrderByOrderId(Long id) throws Exception;
}
