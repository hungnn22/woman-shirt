package com.ws.master_service.dto.admin.order.search;

import lombok.Builder;
import lombok.Data;

/**
 * @author myname
 */

@Data
@Builder
public class OptionDto {
    private String status;
    private String clazz;
    private String name;
}
