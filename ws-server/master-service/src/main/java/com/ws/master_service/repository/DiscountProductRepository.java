package com.ws.master_service.repository;

import com.ws.master_service.entity.DiscountProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountProductRepository extends JpaRepository<DiscountProductEntity, String> {
}
