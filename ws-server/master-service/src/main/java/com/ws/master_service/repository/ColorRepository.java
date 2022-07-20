package com.ws.master_service.repository;

import com.ws.master_service.dto.customer.product.ColorResponse;
import com.ws.master_service.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author myname
 */
@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, String> {
    ColorEntity findByIdAndActive(String id, Boolean active);

    Boolean existsByIdAndActive(String id, Boolean active);

    @Query("select distinct c.name \n" +
            "from ColorEntity c\n" +
            "left join ProductOptionEntity po on po.colorId = c.id\n" +
            "where po.productId = ?1")
    List<String> findByProductId(String productId);

    @Query("select distinct c.name \n" +
            "from ColorEntity c\n" +
            "left join ProductOptionEntity po on po.colorId = c.id\n" +
            "where po.productId = ?1")
    List<String> findDistinctByProductId(String productId);

    @Query("select DISTINCT new com.ws.master_service.dto.customer.product.ColorResponse(" +
            "c.id,\n" +
            "c.name)\n" +
            "from ColorEntity c")
    List<ColorResponse> findAllColor();
}
