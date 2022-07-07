package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.cart.response.CartResponse;
import com.ws.masterserver.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, String> {
    CartEntity findByUserId(String userId);
    Optional<CartEntity> findByUserIdAndAndProductOptionId(String userId, String productOptionId);

    @Query("SELECT new com.ws.masterserver.dto.customer.cart.response.CartResponse(" +
            "c.id,po.id,p.name,po.size,cl.name,c.quantity,po.price,po.image,(po.price * c.quantity))\n"+
            "from CartEntity c\n"+
            "JOIN ProductOptionEntity po ON c.productOptionId = po.id\n" +
            "JOIN ColorEntity cl ON po.colorId = cl.id\n" +
            "JOIN ProductEntity p ON po.productId = p.id\n" +
            "WHERE c.userId = :userId")
    List<CartResponse> getListCart(@Param("userId") String userId);

    @Modifying
    @Query("DELETE FROM CartEntity c WHERE c.productOptionId = :productOptionId")
    void deleteByProductOptionId(@Param("productOptionId") String productOptionId);

    @Query("SELECT COUNT(c.productOptionId) FROM CartEntity c WHERE c.userId = :userId")
    Integer countItemInCart(@Param("userId") String userId);

}
