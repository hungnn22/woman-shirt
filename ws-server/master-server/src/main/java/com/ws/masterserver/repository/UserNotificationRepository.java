package com.ws.masterserver.repository;

import com.ws.masterserver.entity.UserNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotificationEntity, String> {

    Boolean existsByUserIdAndNotificationId(String userId, String notificationId);
}
