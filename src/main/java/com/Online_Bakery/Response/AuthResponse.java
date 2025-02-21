package com.Online_Bakery.Response;

import com.Online_Bakery.Model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private USER_ROLE role;
}
