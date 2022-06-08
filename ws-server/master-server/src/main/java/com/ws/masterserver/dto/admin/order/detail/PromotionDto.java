package com.ws.masterserver.dto.admin.order.detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author hungnn22
 * Class đang map tới method findByOrderId trong OrderPromotionRepository
 * Nếu move class này thì cần copy đường dẫn lại trong câu jpql trong OrderPromotionRepository
 * */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDto {
    private String voucher;
    private Double percentDiscount;
    private String name;
    private String typeName;
    private String typeCode;
}
