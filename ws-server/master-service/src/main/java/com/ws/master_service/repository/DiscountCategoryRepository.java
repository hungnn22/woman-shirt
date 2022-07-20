package com.ws.master_service.repository;

import com.ws.master_service.entity.DiscountCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountCategoryRepository extends JpaRepository<DiscountCategoryEntity, String> {
}
