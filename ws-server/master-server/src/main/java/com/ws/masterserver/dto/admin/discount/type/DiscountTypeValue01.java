package com.ws.masterserver.dto.admin.discount.type;

import lombok.Data;

@Data
public class DiscountTypeValue01 extends DiscountTypeDto {

    /**
     * Giá trị km
     */
    private String percentageValue;

    /**
     * Giá trị giảm tối đa
     */
    private String valueLimitAmount;
}
