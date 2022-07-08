package com.ws.masterserver.repository;

import com.ws.masterserver.dto.admin.product.search.ProductRes;
import com.ws.masterserver.dto.customer.product.ProductReq;
import com.ws.masterserver.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    List<ProductEntity> findByCategoryIdAndActive(String productId, Boolean active);

    @Query("select new com.ws.masterserver.dto.admin.product.search.ProductRes.ProductSubRes(\n" +
            "p1.id as p1Id,\n" +
            "p1.name as p1Name,\n" +
            "(select min(po1.price) from ProductOptionEntity po1 where po1.productId = p1.id) as p1MinPrice,\n" +
            "(select max(po1.price) from ProductOptionEntity po2 where po2.productId = p1.id) as p1MaxPrice,\n" +
            "(select sum(po3.qty) from ProductOptionEntity po3 where po3.productId = p1.id) as p1Qty,\n" +
            "p1.des as p1Des,\n" +
            "p1.active as p1Active,\n" +
            "p1.createdDate as p1CreatedDate,\n" +
            "m1.name as m1Name,\n" +
            "ct1.name as ct1Name,\n" +
            "t1.name as t1Name)\n" +
            "from PromotionEntity p1\n" +
            "left join MaterialEntity m1 on m1.id = p1.materialId\n" +
            "left join CategoryEntity ct1 on ct1.id = p1.categoryId\n" +
            "left join TypeEntity t1 on t1.id = ct1.typeId\n")
    List<ProductRes.ProductSubRes> searchProductSubRes4Admin(ProductReq req);
}
