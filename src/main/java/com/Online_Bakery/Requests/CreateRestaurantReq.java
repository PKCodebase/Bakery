package com.Online_Bakery.Requests;

import com.Online_Bakery.Model.Address;
import com.Online_Bakery.Model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantReq {
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> imagesList;
    private boolean open;
}
