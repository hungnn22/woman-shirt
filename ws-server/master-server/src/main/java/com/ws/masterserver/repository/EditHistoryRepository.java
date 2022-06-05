package com.ws.masterserver.repository;

import com.ws.masterserver.entity.EditHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditHistoryRepository extends JpaRepository<EditHistoryEntity, String> {
}
