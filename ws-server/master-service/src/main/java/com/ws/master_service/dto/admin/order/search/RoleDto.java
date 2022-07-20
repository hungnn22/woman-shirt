package com.ws.master_service.dto.admin.order.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author myname
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleDto {
    private String code;
    private String name;
}
