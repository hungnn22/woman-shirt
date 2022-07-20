package com.ws.masterserver.repository;

import com.ws.masterserver.entity.DiscountCustomerTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountCustomerTypeRepository extends JpaRepository<DiscountCustomerTypeEntity, String> {
}
