package com.ws.master_service.repository;

import com.ws.master_service.entity.DiscountCustomerTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountCustomerTypeRepository extends JpaRepository<DiscountCustomerTypeEntity, String> {
}
