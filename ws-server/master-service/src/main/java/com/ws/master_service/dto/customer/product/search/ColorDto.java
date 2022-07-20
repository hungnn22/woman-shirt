package com.ws.master_service.dto.customer.product.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColorDto {
    private String name;
    private String hex;
}
