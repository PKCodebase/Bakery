package com.Online_Bakery.Services;

import com.Online_Bakery.Model.Order;
import com.Online_Bakery.Response.PaymentResponse;
import com.stripe.exception.StripeException;


public interface PaymentService {
    public PaymentResponse createPaymentLink(Order order) throws StripeException;
}
