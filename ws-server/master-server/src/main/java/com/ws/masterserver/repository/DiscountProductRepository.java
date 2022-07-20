package com.ws.masterserver.repository;

import com.ws.masterserver.entity.DiscountProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountProductRepository extends JpaRepository<DiscountProductEntity, String> {
}
