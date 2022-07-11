package com.ws.masterserver.repository;

import com.ws.masterserver.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    List<ProductEntity> findByCategoryIdAndActive(String productId, Boolean active);

    @Query("select count(od.productOptionId)\n" +
            "from OrderDetailEntity od\n" +
            "left join OrderEntity o on o.id = od.orderId and o.payed = true\n" +
            "left join ProductOptionEntity po on po.id = od.productOptionId\n" +
            "left join PromotionEntity p on p.id = po.productId and p.active = true\n" +
            "where p.id = :id\n" +
            "group by p.id")
    Long countSellNumber(@Param("id") String id);
}
