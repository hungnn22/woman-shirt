package com.ws.masterserver.dto.admin.product_option;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionResponse {
    private String id;
    private String productName;
    private String sizeName;
    private String colorName;
    private Long qty;
    private Long price;
    private String image;
}