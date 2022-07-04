package com.ws.masterserver.repository;

import com.ws.masterserver.entity.NotificationEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, String> {

    @Query("select no\n" +
            "from NotificationEntity no\n" +
            "where no.userId is null")
    List<NotificationEntity> find4Elements4Admin(Pageable pageable);

    @Query("select n.id from NotificationEntity n where n.userId is null")
    List<String> find4Admin();
}
