package com.ws.master_service.dto.customer.product.search;

import com.ws.master_service.utils.base.rest.PageReq;
import lombok.Data;

import java.util.List;

@Data
public class ProductReq {
    private String textSearch;
    private String minPrice;
    private String maxPrice;
    private List<String> sizeIds;
    private List<String> colorIds;
    private PageReq pageReq;
}
