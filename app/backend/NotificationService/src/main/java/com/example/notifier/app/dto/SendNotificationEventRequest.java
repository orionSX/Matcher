package com.example.notifier.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendNotificationEventRequest {
    private String userId;
    private String event; //  null
    private String message; //  null
}