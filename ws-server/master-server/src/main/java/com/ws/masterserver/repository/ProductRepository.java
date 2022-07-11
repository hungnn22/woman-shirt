package com.ws.masterserver.repository;

import com.ws.masterserver.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    List<ProductEntity> findByCategoryIdAndActive(String productId, Boolean active);

}
