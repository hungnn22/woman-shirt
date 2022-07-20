package com.ws.master_service.repository;

import com.ws.master_service.entity.ResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetTokenEntity, String> {
}
