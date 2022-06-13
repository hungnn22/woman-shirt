package com.ws.masterserver.repository;

import com.ws.masterserver.entity.PromotionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionTypeRepository extends JpaRepository<PromotionTypeEntity, String> {
}
