package com.ws.masterserver.repository;

import com.ws.masterserver.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, String> {
    Boolean existsByWardCodeAndExactIgnoreCase(String wardCode, String trim);
}
