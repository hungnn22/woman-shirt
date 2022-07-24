package com.ws.masterserver.dto.admin.discount.type;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName("DT01")
public class DiscountTypeValue01 extends DiscountTypeDto {

    /**
     * Giá trị km
     */
    private String percentageValue;

    /**
     * Giá trị giảm tối đa
     */
    private String valueLimitAmount;

    @Override
    public String getChildType() {
        return "DT01";
    }
}
