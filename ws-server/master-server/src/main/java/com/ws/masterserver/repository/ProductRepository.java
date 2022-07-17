package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.product.ProductRelatedRes;
import com.ws.masterserver.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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


    @Query(value = "select distinct on (p.id) p.id AS productId,p.name AS productName,po.price AS price,po.image AS image \n" +
            "from product p \n" +
            "join category c on p.category_id = c.id \n" +
            "join product_option po on p.id = po.product_id \n" +
            "where p.category_id = ?1",nativeQuery = true)
    List<ProductRelatedRes> getProductRelated(String categoryId);

    @Modifying
    @Query(value = "update product\n" +
            "set view_number = view_number + 1\n" +
            "where id = ?1", nativeQuery = true)
    void increaseViewNumberByProductOptionId(String id);
}
