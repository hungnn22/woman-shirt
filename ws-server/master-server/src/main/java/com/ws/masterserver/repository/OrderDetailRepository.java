package com.ws.masterserver.repository;

import com.ws.masterserver.dto.admin.order.detail.ItemDto;
import com.ws.masterserver.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, String> {

    @Query("select new com.ws.masterserver.dto.admin.order.detail.ItemDto(\n" +
            "p1.id,\n" +
            "p1.name,\n" +
            "c1.name,\n" +
            "s1.name,\n" +
            "po1.image,\n" +
            "m1.name,\n" +
            "po1.price,\n" +
            "po1.qty,\n" +
            "ct1.name,\n" +
            "po1.qty * po1.price)\n" +
            "from OrderDetailEntity od1\n" +
            "left join ProductOptionEntity po1 on po1.id = od1.productOptionId\n" +
            "left join ProductEntity p1 on p1.id = po1.productId\n" +
            "left join ColorEntity c1 on c1.id = po1.colorId\n" +
            "left join SizeEntity s1 on s1.id = po1.sizeId\n" +
            "left join CategoryEntity ct1 on ct1.id = p1.categoryId\n" +
            "left join MaterialEntity m1 on m1.id = p1.materialId\n" +
            "where od1.orderId = :orderId")
    List<ItemDto> getItemList(@Param("orderId") String orderId);
}
