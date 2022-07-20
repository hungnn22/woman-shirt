package com.ws.masterserver.repository;

import com.ws.masterserver.entity.DiscountCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountCategoryRepository extends JpaRepository<DiscountCategoryEntity, String> {
}
