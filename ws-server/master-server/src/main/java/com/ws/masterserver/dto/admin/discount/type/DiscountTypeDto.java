package com.ws.masterserver.dto.admin.discount.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "@ttype")
@Data
@JsonSubTypes({
        @JsonSubTypes.Type(value = DiscountTypeValue01.class, name = "DT01"),
        @JsonSubTypes.Type(value = DiscountTypeValue02.class, name = "DT02"),
        @JsonSubTypes.Type(value = DiscountTypeValue03.class, name = "DT03")
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class DiscountTypeDto {
    @JsonProperty("@ttype")
    public abstract String getChildType();
}
