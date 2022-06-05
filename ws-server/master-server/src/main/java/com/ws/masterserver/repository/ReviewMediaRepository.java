package com.ws.masterserver.repository;

import com.ws.masterserver.entity.ReviewMediaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewMediaRepository extends JpaRepository<ReviewMediaEntity, String> {
}
