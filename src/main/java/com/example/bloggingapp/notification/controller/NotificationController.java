package com.example.bloggingapp.notification.controller;

import com.example.bloggingapp.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> createNotification() {
        String response = notificationService.createNotificationConfig();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteNotification() {
        notificationService.deleteNotificationConfig();
        return ResponseEntity.ok("Your request has been processed. You have been unsubscribed from our email notifications."
        );
    }


}
