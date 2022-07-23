package com.ws.masterserver.dto.admin.discount.prerequisite;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrerequisiteType01 extends PrerequisiteDto{
    /**
     * Tổng giá trị đơn hàng tối thiêu
     */
    private Long minimumSaleTotalPrice;
}