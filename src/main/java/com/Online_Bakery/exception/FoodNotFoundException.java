package com.Online_Bakery.exception;

import org.springframework.http.HttpStatus;

public class FoodNotFoundException extends RuntimeException {
    public FoodNotFoundException(HttpStatus notFound,String message) {
        super(message);
    }
}
