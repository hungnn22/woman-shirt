package com.ws.masterserver.repository;

import com.ws.masterserver.entity.DiscountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<DiscountEntity, String> {
    @Query("select d\n" +
            "from DiscountEntity d\n" +
            "where (d.code like :textSearch or d.des like :textSearch)\n" +
            "and (:status is null or d.status = :status)")
    Page<DiscountEntity> search(@Param("textSearch") String textSearch,
                                @Param("status") String status, Pageable pageReq);

    @Query("select count(o.id)\n" +
            "from OrderDiscountEntity o\n" +
            "where o.discountId = ?1")
    Long findUsedNumber(String id);

    @Query("select (count(d) > 0) from DiscountEntity d " +
            "where upper(d.code) = upper(?1) and d.status <> ?2 and d.deleted = ?3")
    boolean existsByCodeIgnoreCaseAndStatusNotAndDeleted(String code, String status, boolean b);

    @Query("select d\n" +
            "from DiscountEntity d\n" +
            "left join DiscountTypeEntity dt on dt.id = d.discountTypeId\n" +
            "where dt.active = true\n" +
            "and d.deleted = false\n" +
            "and dt.name = :name")
    List<DiscountEntity> findByDiscountTypeName4NewCustomer(@Param("name") String name);
}
