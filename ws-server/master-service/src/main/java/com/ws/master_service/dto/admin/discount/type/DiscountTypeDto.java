package com.ws.master_service.dto.admin.discount.type;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@Data
@JsonSubTypes({
        @JsonSubTypes.Type(value = DiscountTypeValue01.class, name = "DT01"),
        @JsonSubTypes.Type(value = DiscountTypeValue02.class, name = "DT02"),
        @JsonSubTypes.Type(value = DiscountTypeValue03.class, name = "DT03")
})
public class DiscountTypeDto {
}
