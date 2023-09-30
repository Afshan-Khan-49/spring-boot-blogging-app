package com.example.bloggingapp.notification.service;

import com.example.bloggingapp.common.util.LoginUtils;
import com.example.bloggingapp.notification.entity.NotificationConfig;
import com.example.bloggingapp.notification.enums.NotificationType;
import com.example.bloggingapp.notification.repository.NotificationRepository;
import com.example.bloggingapp.ses.SESHelper;
import com.example.bloggingapp.user.entity.User;
import com.example.bloggingapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final SESHelper sesHelper;
    @Override
    public String createNotificationConfig() {
        User user = userService.checkIfUserExists(LoginUtils.getCurrentUserEmail());
        boolean notificationExists = checkIfNotificationExists(user);
        if(notificationExists)
            return "You are already subscribed to email notifications";
        notificationRepository.save(NotificationConfig.builder()
                        .notificationType(NotificationType.EMAIL)
                        .user(user)
                        .build());
        return "Success! You have been subscribed to email notifications." +
                " We're excited to keep you updated. You can change your preference anytime." +
                "Please check your inbox for verification email";
    }

    private boolean checkIfNotificationExists(User user) {
        return notificationRepository.existsByUserAndNotificationType(user, NotificationType.EMAIL);
    }

    @Override
    public void deleteNotificationConfig() {
        User user = userService.checkIfUserExists(LoginUtils.getCurrentUserEmail());
        Optional<NotificationConfig> byRecipient = notificationRepository.findByUserAndNotificationType(user,NotificationType.EMAIL);
        byRecipient.ifPresent(notificationRepository::delete);
    }

    @Override
    public void sendNotification(String subject, String body, String from, String to) {
        log.info("Sending post notification to {}",to);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);
        sesHelper.sendEmail(simpleMailMessage);
    }

    @Override
    @Async
    public void notifyUsers(String subject, String body, String from, Long userId) {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        while (true) {
            Page<NotificationConfig> notificationConfigs = notificationRepository.findByUserFollowers(userId, pageRequest);
            List<NotificationConfig> notificationConfigsContent = notificationConfigs.getContent();
            notificationConfigsContent.forEach(config -> sendNotification(subject,formatBody(body,config.getUser()),from,config.getUser().getEmail()));
            if(!notificationConfigs.hasNext())
                break;
            pageRequest = pageRequest.next();
        }
    }

    private String formatBody(String body, User user) {

        return "Hi " + (StringUtils.isNotEmpty(user.getFirstName()) ? user.getFirstName() : "User") + " " +
                (StringUtils.isNotEmpty(user.getLastName()) ? user.getLastName() + "" : "") + ",\n" +
                body;
    }
}
