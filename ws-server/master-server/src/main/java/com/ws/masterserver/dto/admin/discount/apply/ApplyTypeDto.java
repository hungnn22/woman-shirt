package com.ws.masterserver.dto.admin.discount.apply;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@Data
@JsonSubTypes({
        @JsonSubTypes.Type(value = ApplyTypeValue01.class, name = "AT01"),
        @JsonSubTypes.Type(value = ApplyTypeValue02.class, name = "AT02"),
        @JsonSubTypes.Type(value = ApplyTypeValue03.class, name = "AT03")
})
public class ApplyTypeDto {
}
