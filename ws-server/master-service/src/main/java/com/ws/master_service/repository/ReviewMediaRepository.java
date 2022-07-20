package com.ws.master_service.repository;

import com.ws.master_service.entity.ReviewMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewMediaRepository extends JpaRepository<ReviewMediaEntity, String> {
}
