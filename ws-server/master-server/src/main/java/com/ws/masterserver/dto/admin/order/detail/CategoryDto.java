package com.ws.masterserver.dto.admin.order.detail;

import lombok.Builder;
import lombok.Data;

/**
 * @author hungnn22
 */
@Builder
@Data
public class CategoryDto {
    private String id;
    private String name;
    private String des;
}
