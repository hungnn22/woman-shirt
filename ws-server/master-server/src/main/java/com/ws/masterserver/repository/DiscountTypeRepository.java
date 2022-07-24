package com.ws.masterserver.repository;

import com.ws.masterserver.entity.DiscountTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountTypeRepository extends JpaRepository<DiscountTypeEntity, String> {
    @Query("select d from DiscountTypeEntity d where d.name = ?1")
    DiscountTypeEntity findByName(String name);
}
