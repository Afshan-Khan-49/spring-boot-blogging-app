package com.example.bloggingapp.notification.service;

public interface NotificationService {
    String createNotificationConfig();
    void deleteNotificationConfig();
    void sendNotification(String subject, String body, String from, String to);
    void notifyUsers(String subject, String body, String from, Long userId);
}
