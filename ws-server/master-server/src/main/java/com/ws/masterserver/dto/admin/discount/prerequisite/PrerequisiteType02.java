package com.ws.masterserver.dto.admin.discount.prerequisite;

import com.ws.masterserver.dto.admin.discount.prerequisite.PrerequisiteDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrerequisiteType02 extends PrerequisiteDto {
    private Long minimumQuantity;
}