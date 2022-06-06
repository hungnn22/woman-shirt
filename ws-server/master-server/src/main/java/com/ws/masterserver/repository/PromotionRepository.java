package com.ws.masterserver.repository;

import com.ws.masterserver.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author myname
 */
@Repository
public interface PromotionRepository extends JpaRepository<PromotionEntity, String> {
}
