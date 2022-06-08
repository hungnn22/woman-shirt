package com.ws.masterserver.repository;

import com.ws.masterserver.dto.admin.order.detail.PromotionDto;
import com.ws.masterserver.entity.OrderPromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author hungnn22
 */
public interface OrderPromotionRepository extends JpaRepository<OrderPromotionEntity, String> {

    @Query("select new com.ws.masterserver.dto.admin.order.detail.PromotionDto(\n" +
            "p.voucher,\n" +
            "p.percentDiscount,\n" +
            "p.name,\n" +
            "pt.name,\n" +
            "pt.code)\n" +
            "from OrderPromotionEntity op\n" +
            "left join PromotionEntity p on op.promotionId = p.id\n" +
            "left join PromotionTypeEntity pt on p.promotionTypeId = pt.id\n" +
            "where op.orderId = :orderId")
    List<PromotionDto> findByOrderId(@Param("orderId") String orderId);

}
