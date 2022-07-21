package com.ws.master_service.dto.admin.discount.prerequisite;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrerequisiteType01 extends PrerequisiteDto{
    /**
     * Tổng giá trị đơn hàng tối thiêu
     */
    private String minimumSaleTotalPrice;
}
