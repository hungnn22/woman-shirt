package com.ws.master_service.dto.admin.discount.prerequisite;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrerequisiteType02 extends PrerequisiteDto {
    private String minimumQuantity;
}
