package com.ws.masterserver.repository;

import com.ws.masterserver.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
}
