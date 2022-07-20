package com.ws.master_service.repository;

import com.ws.master_service.entity.EditHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditHistoryRepository extends JpaRepository<EditHistoryEntity, String> {
}
