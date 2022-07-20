package com.ws.masterserver.repository;

import com.ws.masterserver.entity.EmailLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailLogRepository extends JpaRepository<EmailLogEntity, String> {
}
