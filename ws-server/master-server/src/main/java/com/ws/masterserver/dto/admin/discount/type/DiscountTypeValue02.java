package com.ws.masterserver.dto.admin.discount.type;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

@Data
@JsonTypeName("DT02")
public class DiscountTypeValue02 extends DiscountTypeDto {
    /**
     * Giá trị KM
     */
    private String amountValue;

    @Override
    public String getChildType() {
        return "DT02";
    }
}
