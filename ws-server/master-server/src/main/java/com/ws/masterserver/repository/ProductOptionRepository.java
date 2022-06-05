package com.ws.masterserver.repository;

import com.ws.masterserver.entity.ProductOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOptionEntity, String> {

    @Query("SELECT o FROM ProductOptionEntity o WHERE o.productId = :id")
    List<ProductOptionEntity> findByProductId(@Param("id") String id);

    List<ProductOptionEntity> findByProductIdAndActive(String productId, Boolean active);
}
