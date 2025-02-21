package com.Online_Bakery.Requests;

import com.Online_Bakery.Model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
}
