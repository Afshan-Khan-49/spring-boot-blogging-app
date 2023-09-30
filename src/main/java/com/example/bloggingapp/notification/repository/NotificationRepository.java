package com.example.bloggingapp.notification.repository;

import com.example.bloggingapp.notification.entity.NotificationConfig;
import com.example.bloggingapp.notification.enums.NotificationType;
import com.example.bloggingapp.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationConfig, Integer> {
    Optional<NotificationConfig> findByUserAndNotificationType(User user, NotificationType notificationType);
    boolean existsByUserAndNotificationType(User user, NotificationType notificationType);


    @Query("Select nc from NotificationConfig nc join Follow f on nc.user.id = f.from where f.to.id = :userId")
    @EntityGraph(attributePaths = "user")
    Page<NotificationConfig> findByUserFollowers(Long userId, Pageable pageable);

}
