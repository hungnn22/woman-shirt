package com.ws.masterserver.repository;

import com.ws.masterserver.dto.customer.product.ColorResponse;
import com.ws.masterserver.entity.ProductOptionEntity;
import com.ws.masterserver.utils.constants.enums.SizeEnum;
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

    @Query(value = "SELECT po.id FROM product_option po WHERE po.size = ?1 and po.color_id = ?2",nativeQuery = true)
    String findBySizeAndColorId(String size,String color);

//    @Query(value = "SELECT DISTINCT c.id AS colorId, c.name AS colorName\n" +
//            "FROM product_option po\n" +
//            "JOIN color c on po.color_id = c.id\n" +
//            "WHERE size = ?1",nativeQuery = true)

     //List<ColorResponse> getListColorNameBySize(@Param("size") String size);

    @Query("select DISTINCT new com.ws.masterserver.dto.customer.product.ColorResponse(" +
        "c.id,\n" +
        "c.name)\n" +
        "from ProductOptionEntity po\n" +
        "JOIN ColorEntity c on po.colorId = c.id\n" +
        "where po.size = :size")
    List<ColorResponse> getListColorNameBySize(@Param("size") SizeEnum size);
}
