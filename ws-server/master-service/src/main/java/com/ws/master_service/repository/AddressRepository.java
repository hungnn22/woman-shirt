package com.ws.master_service.repository;

import com.ws.master_service.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, String> {
    Boolean existsByWardCodeAndExactIgnoreCase(String wardCode, String trim);
}
