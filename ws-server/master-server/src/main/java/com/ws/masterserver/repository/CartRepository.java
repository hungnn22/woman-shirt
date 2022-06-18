package com.ws.masterserver.repository;

import com.ws.masterserver.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, String> {
    CartEntity findByUserId(String userId);
    Optional<CartEntity> findByUserIdAndAndProductOptionId(String userId, String productOptionId);
}
