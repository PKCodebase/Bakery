package com.Online_Bakery.Response;

import lombok.Data;

@Data
public class MessageResponse {
    private String message;

    public MessageResponse() {
    }

    // Constructor with message parameter
    public MessageResponse(String message) {
        this.message = message;
    }

    // Getter and Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
