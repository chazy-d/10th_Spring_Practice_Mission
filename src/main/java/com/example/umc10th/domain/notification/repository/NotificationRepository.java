package com.example.umc10th.domain.notification.repository;

import com.example.umc10th.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
