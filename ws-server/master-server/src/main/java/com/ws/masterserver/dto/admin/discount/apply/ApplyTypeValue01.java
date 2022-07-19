package com.ws.masterserver.dto.admin.discount.apply;

import lombok.Data;

@Data
public class ApplyTypeValue01 {

    /**
     * Giá trị km
     */
    private String percentageValue;

    /**
     * Giá trị giảm tối đa
     */
    private String valueLimitAmount;
}
