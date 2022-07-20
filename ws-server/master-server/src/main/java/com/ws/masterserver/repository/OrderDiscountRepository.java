package com.ws.masterserver.repository;

import com.ws.masterserver.entity.OrderDiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDiscountRepository extends JpaRepository<OrderDiscountEntity, String> {
}
