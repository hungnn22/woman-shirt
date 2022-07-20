package com.ws.master_service.repository;

import com.ws.master_service.dto.customer.size.response.SizeResponse;
import com.ws.master_service.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity, String> {

    @Query("select distinct s.name\n" +
            "from SizeEntity s\n" +
            "left join ProductOptionEntity po on po.sizeId = s.id\n" +
            "where po.productId = ?1")
    List<String> findByProductId(String productId);

    String findNameById(String sizeId);

    @Query("select distinct s.name\n" +
            "from SizeEntity s\n" +
            "left join ProductOptionEntity po on po.sizeId = s.id\n" +
            "where po.productId = ?1")
    List<String> findDistinctByProductId(String productId);


    @Query("select DISTINCT new com.ws.master_service.dto.customer.size.response.SizeResponse(" +
            "s.id,\n" +
            "s.code)\n" +
            "from SizeEntity s")
    List<SizeResponse> getAllSize();

}
