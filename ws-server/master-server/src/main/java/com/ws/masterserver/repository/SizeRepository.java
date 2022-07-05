package com.ws.masterserver.repository;

import com.ws.masterserver.entity.SizeEntity;
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
}
