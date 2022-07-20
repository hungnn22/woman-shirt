package com.ws.masterserver.repository;

import com.ws.masterserver.entity.ResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetTokenRepository extends JpaRepository<ResetTokenEntity, String> {
}
