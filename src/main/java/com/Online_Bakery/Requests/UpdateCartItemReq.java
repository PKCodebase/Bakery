package com.Online_Bakery.Requests;

import lombok.Data;

@Data
public class UpdateCartItemReq {
    private Long cartItemId;
    private int quantity;
}
