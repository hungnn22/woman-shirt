package com.ws.masterserver.dto.admin.product.search;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductRes {
    private String id;
    private String name;
    private String des;
    private String minPrice;
    private String maxPrice;
    private Boolean active;
    private String createdDate;
    private Long qty;
    private String materialName;
    private String categoryName;
    private String typeName;
    private Long soldNumber;
    private List<String> sizes;
    private List<String> colors;
}
