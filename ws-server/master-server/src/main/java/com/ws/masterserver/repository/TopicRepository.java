package com.ws.masterserver.repository;

import com.ws.masterserver.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, String> {
    TopicEntity findByName(String name);

    TopicEntity findByID(String id);
}
