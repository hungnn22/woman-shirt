package com.ws.masterserver.dto.admin.order.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
