package com.ws.masterserver.repository;

import com.ws.masterserver.entity.DiscountEntity;
import com.ws.masterserver.utils.constants.enums.DiscountStatusEnums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Query("select (count(d.id) > 0) from DiscountEntity d where upper(d.code) = upper(?1) and d.status <> ?2")
    boolean existsByCodeIgnoreCaseAndStatusNot(String code, String status);
}
