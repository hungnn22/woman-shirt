package com.ws.masterserver.dto.admin.discount.prerequisite;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@Data
@JsonSubTypes({
        @JsonSubTypes.Type(value = PrerequisiteType01.class, name = "PT01"),
        @JsonSubTypes.Type(value = PrerequisiteType02.class, name = "PT02")
})
public class PrerequisiteDto {
}