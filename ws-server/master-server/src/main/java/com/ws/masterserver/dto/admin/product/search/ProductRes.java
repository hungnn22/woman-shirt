package com.ws.masterserver.dto.admin.product.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRes {
    private String id;
    private String name;
    private String des;
    private String price;
    private String qty;
    private String material;
    private String category;
    private String type;
    private String status;
}
