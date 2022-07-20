package com.ws.master_service.repository;

import com.ws.master_service.entity.BodyHeightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyHeightRepository extends JpaRepository<BodyHeightEntity, String> {

    @Query("select h from BodyHeightEntity h where h.minHeight <= ?1 and h.maxHeight >= ?1")
    BodyHeightEntity findByHeight(Long height);
}
