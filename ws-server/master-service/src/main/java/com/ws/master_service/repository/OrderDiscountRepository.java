package com.ws.master_service.repository;

import com.ws.master_service.entity.OrderDiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDiscountRepository extends JpaRepository<OrderDiscountEntity, String> {
}
